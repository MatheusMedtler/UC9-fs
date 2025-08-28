package Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name="pokemons")

public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pokemon")
    private int id;

    @Column(name = "fk_id_treinador", nullable = true)
    private Integer fk_id_treinador;

    @Column(name = "nome", nullable = false)
    private String nome;


    @Column(name = "tipo_primario",nullable = false)
    @JsonProperty("tipoPrimario")
    private String tipoPrimario;

    @Column(name = "tipo_secundario",nullable = true)
    @JsonProperty("tipoSecundario")
    private String tipoSecundario;


    @Column(name = "nivel",nullable = false)
    private int nivel;
    @Column(name = "hp_maximo",nullable = false)
    private int hpMaximo;
    @Column(name = "hp_atual",nullable = false)
    private int hpAtual;

    public Pokemon(int id, String nome, String tipoPrimario, String tipoSecundario, int nivel, int hp, int hpAtual) {
        this.id = id;
        this.nome = nome;
        this.tipoPrimario = tipoPrimario;
        this.tipoSecundario = tipoSecundario;
        this.nivel = nivel;
        this.hpMaximo = hp;
        this.hpAtual = hpAtual;
    }
    public Pokemon( String nome, String tipoPrimario, String tipoSecundario, int nivel, int hp, int hpAtual) {
        this.nome = nome;
        this.tipoPrimario = tipoPrimario;
        this.tipoSecundario = tipoSecundario;
        this.nivel = nivel;
        this.hpMaximo = hp;
        this.hpAtual = hpAtual;
    }

    public Pokemon() {
    }


    public int getHpAtual() {
        return hpAtual;
    }

    public void setHpAtual(int hpAtual) {
        this.hpAtual = hpAtual;
    }

    public int getId_pokemon() {
        return id;
    }

    public void setId_pokemon(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo_primario() {
        return tipoPrimario;
    }

    public void setTipo_primario(String tipoPrimario) {
        this.tipoPrimario = tipoPrimario;
    }

    public String getTipo_secundario() {
        return tipoSecundario;
    }

    public void setTipo_secundario(String tipoSecundario) {
        this.tipoSecundario = tipoSecundario;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        if(nivel < 1 || nivel > 100){
            throw new IllegalArgumentException("O nivel deve ser entre 1 e 100");
        }
        this.nivel = nivel;
    }

    public int getHpMaximo() {
        return hpMaximo;
    }

    public void setHpMaximo(int hp) {
        if(hp <= 0){
            throw new IllegalArgumentException("O hp maximo deve ser maior que 0");
        }
        this.hpMaximo = hp;
    }
}