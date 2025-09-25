package sistemaOperacionalFase2;

public class WorstFit implements AlgoritmoAlocacao {
	public boolean alocar(Memoria memoria, Processo processo) {
		BlocoMemoria pior = null;

		for (BlocoMemoria b : memoria.getBlocos()) {
			if (b.livre && b.tamanho >= processo.getTamanho()) {
				if (pior == null || b.tamanho > pior.tamanho) {
					pior = b;
				}
			}
		}

		if (pior != null) {
			memoria.dividirBloco(pior, processo);
			return true;
		}
		return false;
	}
}
