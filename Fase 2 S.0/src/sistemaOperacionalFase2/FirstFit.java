package sistemaOperacionalFase2;

import java.util.ListIterator;

public class FirstFit implements AlgoritmoAlocacao {
	public boolean alocar(Memoria memoria, Processo processo) {
		ListIterator<BlocoMemoria> iterator = memoria.getBlocos().listIterator();
		while (iterator.hasNext()) {
			BlocoMemoria b = iterator.next();
			if (b.livre && b.tamanho >= processo.getTamanho()) {
				memoria.dividirBloco(b, processo);
				return true;
			}
		}
		return false;
	}
}
