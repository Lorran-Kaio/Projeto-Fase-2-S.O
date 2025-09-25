package sistemaOperacionalFase2;

public class NextFit implements AlgoritmoAlocacao {
	private int ultimaPosicao = 0;

	public boolean alocar(Memoria memoria, Processo processo) {
		int n = memoria.getBlocos().size();

		for (int i = 0; i < n; i++) {
			int index = (ultimaPosicao + i) % n;
			BlocoMemoria b = memoria.getBlocos().get(index);

			if (b.livre && b.tamanho >= processo.getTamanho()) {
				memoria.dividirBloco(b, processo);
				ultimaPosicao = index;
				return true;
			}
		}
		return false;
	}
}
