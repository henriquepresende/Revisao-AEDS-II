import java.util.NoSuchElementException;

public class TabelaHash<K, V> {
    private static class Entrada<K, V>{
        K chave;
        V valor;
        boolean ativa;

        public Entrada(K chave, V valor){
            this.chave = chave;
            this.valor = valor;
            this.ativa = true;
        }
    }
    private Entrada<K, V>[] tabela;
    private int tamanhoPrincipal;
    private int tamanhoReserva;
    private int itensNaReserva;

    @SupressWarnings("unchecked")
    public TabelaHash(int tamanhoPrincipal, int tamanhoReserva){
        this.tamanhoPrincipal= tamanhoPrincipal;
        this.tamanhoReserva = tamanhoReserva;
        this.itensNaReserva = 0;
        this.tabela = new Entrada[tamanhoPrincipal + tamanhoReserva]
    }

    private int hash(K chave){
        return this.Math.abs(chave.hashCode()) % tamanhoPrincipal
    }

    public void inserir(K chave, V valor){
        int posicao = hash(chave);

        if(tabela[posicao] == null || !tabela[posicao].ativa){
            tabela[posicao] = new Entrada<>(chave,valor);
            return;
        }

        if (tabela[posicao].chave.equals(chave)) {
            tabela[posicao].valor = valor;
            return;
        }

        if (itensNaReserva >= tamanhoReserva) {
            throw new IllegalStateException("A área de reserva está cheia!");
        }

        int posicaoReserva = tamanhoPrincipal + itensNaReserva;
        tabela[posicaoReserva] = new Entrada<>(chave,valor);
        itensNaReserva++;
    }

    public V pesquisar(K chave) {
        int posicao = hash(chave);

        if (tabela[posicao] != null && tabela[posicao].ativa && tabela[posicao].chave.equals(chave)){
            return tabela[posicao].valor;
        }

        for (int i = 0; i< itensNaReserva;i++){
            int posicaoAtual = tamanhoPrincipal + i;
            if (tabela[posicaoAtual] != null && tabela[posicaoAtual].ativa && tabela[posicaoAtual].chave.equals(chave)){
                return tabela[posicaoAtual].valor;
            }
        }

        throw new NoSuchElementException("Chave não encontrada na tabela");
    }

    public V remover (K chave){
        int posicao = hash(chave);

        if (tabela[posicao] != null && tabela[posicao].ativa && tabela[posicao].chave.equals(chave)) {
            V valorRemovido = tabela[posicao].valor;
            tabela[posicao].ativa = false;
            return valorRemovido;
        }

        for (int i = 0; i < itensNaReserva;i++) {
            int posicaoAtual = tamanhoPrincipal + i;
            if(tabela[posicaoAtual] != null && tabela[posicaoAtual].ativa && tabela[posicaoAtual].chave.equals(chave)){
                V valorRemovido = tabela[posicao].valor;
                tabela[posicaoAtual].ativa = false;
                return valorRemovido;
            }
        }

        throw new NoSuchElementException("Chave não encontrada para a remoção!");
    }

    private void restaurar(){
        Entrada<K, V>[] tabelaAntiga = this.tabela;

        this.tabela = new Entrada[tabelaAntiga.length];

        this.tamanho = 0;

        for(int i = 0;i< tabelaAntiga.length;i++){
            Entrada<K, V> entrada = tabelaAntiga[i];

            if (entrada != null && entrada.ativa){
                this.inserir(entrada.chave, entrada.valor);
            }
        }
    }

    public boolean vazia(){
    for (int i = 0; i < this.tabela.length;i++){
        if (this.tabela[i] != null && !this.tabela[i].vazia()){
            return false;
        }
    }

    return true;
    }
}
