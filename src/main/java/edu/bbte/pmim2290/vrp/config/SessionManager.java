package edu.bbte.pmim2290.vrp.config;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
    // username -> token
    private final Map<String, String> sessions = new ConcurrentHashMap<>();

    public boolean isLoggedIn(String username) {
        return sessions.containsKey(username);
    }

    public void registerSession(String username, String token) {
        sessions.put(username, token);
    }

    public void removeSession(String username) {
        sessions.remove(username);
    }

    public boolean validateToken(String username, String token) {
        return token.equals(sessions.get(username));
    }
}
