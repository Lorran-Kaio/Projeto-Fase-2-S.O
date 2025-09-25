package sistemaOperacionalFase2;

public class BestFit implements AlgoritmoAlocacao {
	public boolean alocar(Memoria memoria, Processo processo) {
		BlocoMemoria melhor = null;

		for (BlocoMemoria b : memoria.getBlocos()) {
			if (b.livre && b.tamanho >= processo.getTamanho()) {
				if (melhor == null || b.tamanho < melhor.tamanho) {
					melhor = b;
				}
			}
		}

		if (melhor != null) {
			memoria.dividirBloco(melhor, processo);
			return true;
		}
		return false;
	}
}
