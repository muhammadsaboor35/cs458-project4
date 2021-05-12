package com.project4.cs458.authclient;

import java.util.Arrays;
import java.util.Collections;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

public class KeycloackClient {

    static String serverUrl = "http://localhost:8180/auth";
    static String realm = "quarkus";
    // idm-client needs to allow "Direct Access Grants: Resource Owner Password
    // Credentials Grant"
    static String clientId = "backend-service";
    static String clientSecret = "ee23e3c8-d81d-4c95-bc09-27e3b8cadabb";

    public static Keycloak keycloak = KeycloakBuilder.builder() //
            .serverUrl(serverUrl) //
            .realm(realm) //
            .grantType(OAuth2Constants.CLIENT_CREDENTIALS) //
            .clientId(clientId) //
            .clientSecret(clientSecret)
            .resteasyClient(new ResteasyClientBuilderImpl()
                .connectionPoolSize(10)
                .build()) //
            .build();

    public static void addUser(String username, String password) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(username);
        user.setAttributes(Collections.singletonMap("origin",
        Arrays.asList("demo")));
        
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersRessource = realmResource.users();

        System.out.println(usersRessource);

        Response response = usersRessource.create(user);
        System.out.printf("Repsonse: %s %s%n", response.getStatus(), response.getStatusInfo());
        System.out.println(response.getLocation());
        String userId = CreatedResponseUtil.getCreatedId(response);

        System.out.printf("User created with userId: %s%n", userId);

        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);

        UserResource userResource = usersRessource.get(userId);

        // Set password credential
        userResource.resetPassword(passwordCred);

        // Get realm role "tester" (requires view-realm role)
        RoleRepresentation userRealmRole = realmResource.roles()//
                .get("user").toRepresentation();
        //
        // // Assign realm role tester to user
        userResource.roles().realmLevel() //
                .add(Arrays.asList(userRealmRole));
    }

    public static String getToken(String username, String password) {
        return getAccessTokenString(newKeycloakBuilderWithPasswordCredentials(username, password).build());
    }

    private static String getAccessTokenString(Keycloak keycloak) {
        AccessTokenResponse tokenResponse = getAccessTokenResponse(keycloak);
        return tokenResponse == null ? null : tokenResponse.getToken();
    }

    private static AccessTokenResponse getAccessTokenResponse(Keycloak keycloak) {
        try {
          return keycloak.tokenManager().getAccessToken();
        } catch (Exception ex) {
          return null;
        }
    }

    private static KeycloakBuilder newKeycloakBuilderWithPasswordCredentials(String username, String password) {
        return newKeycloakBuilderWithClientCredentials() //
          .username(username) //
          .password(password) //
          .grantType(OAuth2Constants.PASSWORD);
    }

    private static KeycloakBuilder newKeycloakBuilderWithClientCredentials() {
        return KeycloakBuilder.builder() //
            .serverUrl(serverUrl) //
            .realm(realm) //
            .grantType(OAuth2Constants.CLIENT_CREDENTIALS) //
            .clientId(clientId) //
            .clientSecret(clientSecret);
    }
}
