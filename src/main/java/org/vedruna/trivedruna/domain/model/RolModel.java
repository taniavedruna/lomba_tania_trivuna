package org.vedruna.trivedruna.domain.model;

import lombok.Data;

@Data
public class RolModel {
    
    Integer rolId;
    String rolName;
    String scopes; 
    
    public String getScopes() {
        return scopes;
    }

}
