package sistemaOperacionalFase2;

class Processo {
	private static int nextId = 1;
	private int id;
	private int tamanho;

	public Processo(int tamanho) {
		this.id = nextId++;
		this.tamanho = tamanho;
	}

	public int getId() {
		return id;
	}

	public int getTamanho() {
		return tamanho;
	}
}