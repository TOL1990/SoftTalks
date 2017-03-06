package model.entity;


public class Player {
    private int id;
    private String nickName;
    private String pasword;

    public Player(int id, String firstName, String pasword) {
        this.id = id;
        this.nickName = firstName;
        this.pasword = pasword;
    }

    public Player(String firstName, String pasword) {
        this.nickName = firstName;
        this.pasword = pasword;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", nick='" + nickName + '\'' +
                ", pasword='" + pasword + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (!nickName.equals(player.nickName)) return false;
        return pasword != null ? pasword.equals(player.pasword) : player.pasword == null;
    }

    @Override
    public int hashCode() {
        int result = nickName.hashCode();
        result = 31 * result + (pasword != null ? pasword.hashCode() : 0);
        return result;
    }
}
