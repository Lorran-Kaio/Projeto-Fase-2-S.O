package sistemaOperacionalFase2;

class BlocoMemoria {
	int inicio;
	int tamanho;
	boolean livre;
	Processo processo;

	public BlocoMemoria(int inicio, int tamanho, boolean livre) {
		this.inicio = inicio;
		this.tamanho = tamanho;
		this.livre = livre;
		this.processo = null;
	}
}
