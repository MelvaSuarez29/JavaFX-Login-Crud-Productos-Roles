package org.epn.crudproductosmelvasuarez.modelo;

public class Usuario {
    private int userId;
    private String username;
    private String passwordHash;
    private String rol;

    public Usuario() {}

    public Usuario(int userId, String username, String passwordHash, String rol) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }

    public Usuario(String username, String passwordHash, String rol) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    @Override
    public String toString() {
        return "Usuario{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}