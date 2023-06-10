package guiprova;

public class Viaggi {
    private String nomeSource;
    private String nomeDest;
    private int durata;

    public Viaggi(String nomeSource, String nomeDest, int durata) {
        this.nomeSource = nomeSource;
        this.nomeDest = nomeDest;
        this.durata = durata;
    }

    public String getNomeSource() {
        return nomeSource;
    }

    public String getNomeDest() {
        return nomeDest;
    }

    public int getDurata() {
        return durata;
    }

    public void setNomeSource(String nomeSource) {
        this.nomeSource = nomeSource;
    }

    public void setNomeDest(String nomeDest) {
        this.nomeDest = nomeDest;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }
}
