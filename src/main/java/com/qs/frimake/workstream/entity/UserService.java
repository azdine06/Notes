package com.qs.frimake.workstream.entity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    public boolean isCurrentUserModerator() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Vous devez implémenter la logique pour déterminer si l'utilisateur actuel est un modérateur
        // Par exemple, si vous utilisez Spring Security, vous pouvez vérifier les rôles de l'utilisateur
        // Pour cet exemple, on suppose que seul un utilisateur avec le rôle "MODERATOR" est considéré comme modérateur
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("MODERATOR"));
    }

    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Vous devez implémenter la logique pour obtenir l'ID de l'utilisateur actuel
        // Par exemple, si vous utilisez Spring Security, vous pouvez obtenir les détails de l'utilisateur authentifié
        // Pour cet exemple, on suppose que l'ID de l'utilisateur est stocké dans les détails de l'authentification
        return (String) authentication.getDetails();
    }
}


