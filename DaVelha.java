import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.MatteBorder;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class DaVelha {
	
//  Inserindo icons e painel

	private ImageIcon Opreto = new ImageIcon("imagens/Opreto.png");
	private ImageIcon Xpreto = new ImageIcon("imagens/Xpreto.png");
	private ImageIcon Ocolorido = new ImageIcon("imagens/OColorido.png");
	private ImageIcon Xazul = new ImageIcon("imagens/Xazul.png");
	private int jogada=1;
	private JFrame janela;
	private JPanel painelPrincipal;
	private JPanel painelBotoes;
	private JPanel painelBotoesInferior;
	private JButton[][] matrizBotoes = new JButton[3][3];
	private boolean fimDeJogo = false;
	
	// Montar tela, mostra a interface  gráfica do jogo
	public static void main(String[] args) {
		new DaVelha().montaTela();
	}
	//Chama as janelas, os paineis e os botões na interface.
	public void montaTela() {
		preparaJanela();
		preparaPainelPricipal();
		preparaPainelBotoes();
		preparaPainelBotoesInferior();
		preparaBotaoReset();
		preparaBotaoE();
		exibeTela();
	}
	// Janela da inteface contendo o nome do Game "Jogo da Velha".
	public void preparaJanela() {
		janela = new JFrame("Jogo da Velha UCSAL");
		janela.setLocation(250,150);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void preparaPainelPricipal() {
		painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new BorderLayout());
		preparaTitulo();
		janela.add(painelPrincipal);
	}
	
	public void preparaPainelBotoesInferior() {
		painelBotoesInferior = new JPanel(new GridLayout());
		painelPrincipal.add(painelBotoesInferior,BorderLayout.SOUTH);
	}
	
	public void preparaPainelBotoes() {
		painelBotoes = new JPanel();
		painelBotoes.setLayout(new GridLayout(3,3));
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++) {
				JButton botao = new JButton();
				botao.setBackground(Color.YELLOW);
				ActionListener figuraListener = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(botao.getIcon()==null && fimDeJogo==false){
							if(jogada%2==0) 
								botao.setIcon(Xpreto);
							else 
								botao.setIcon(Opreto);
							jogada++;
							checaFimDeJogo();
						}
					}
				};
				botao.addActionListener(figuraListener);
				painelBotoes.add(botao);
				matrizBotoes[i][j]=botao;
			}
		addBordas();
		painelPrincipal.add(painelBotoes,BorderLayout.CENTER);
	}
	// Botão Exit para encerrar o Game.

	public void preparaBotaoE() {
		JButton botaoExit = new JButton("Exit");
		botaoExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		painelBotoesInferior.add(botaoExit);
	}
	// Botão reset para recomeçar o Game novamente.

	public void preparaBotaoReset() {
		JButton botaoReset = new JButton("Reset");
		botaoReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<3;i++)
					for(int j=0;j<3;j++) {
						matrizBotoes[i][j].setIcon(null);
						matrizBotoes[i][j].setBackground(Color.YELLOW);
					}
				jogada=1;
				fimDeJogo = false;
			}
		});
		painelBotoesInferior.add(botaoReset);
	}
	
	public void exibeTela() {
		//janela.pack();
		janela.setSize(400,400);
		janela.setResizable(false);
		janela.setVisible(true);
	}
	// Indica o titulo do Game, situada na parte superior da interface.

	public void preparaTitulo() {
		JLabel titulo = new JLabel("Jogo da Velha UCSAL",SwingConstants.CENTER);
		titulo.setFont(new Font("Verdana",Font.BOLD,15));
		painelPrincipal.add(titulo,BorderLayout.NORTH);
	}
	// Bordas ou linhas pretas que separam os campos na qual o jogador do Game irá escolher.

	public void addBordas() {
		
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++) {
				int[] bordas = new int[]{0,0,0,0};
				if(i==0)
					bordas[2]=2;
				else if(i==1) {
					bordas[0]=2;
					bordas[2]=2;
				}
				else
					bordas[0]=2;
				if(j==0)
					bordas[3]=2;
				else if(j==1) {
					bordas[1]=2;
					bordas[3]=2;
				}
				else
					bordas[1]=2;
				matrizBotoes[i][j].setBorder(new MatteBorder(bordas[0],bordas[1],bordas[2],bordas[3],Color.BLACK));
			}
	}
	//Checar fim do jogo.

	public void checaFimDeJogo() {
		int contagem = 0;
		//checar se houve ganhador por linhas
		for(int i=0;i<3;i++) {
			int j;
			for(j=0;j<2;j++) {
				if(matrizBotoes[i][j].getIcon()!=null)
					if(matrizBotoes[i][j].getIcon() == matrizBotoes[i][j+1].getIcon())
						contagem++;
			}
			if(contagem==2) {
				//indicando campos que formam o trio
				for(int k=0;k<3;k++) {
					if(matrizBotoes[i][k].getIcon()==Opreto)
						matrizBotoes[i][k].setIcon(Ocolorido);
					else
						matrizBotoes[i][k].setIcon(Xazul);
				}
				exibeGanhador((ImageIcon)matrizBotoes[i][j-1].getIcon());
			}
			contagem = 0;
		}
		
		//checar se houve ganhador por colunas
		for(int i=0;i<3;i++) {
			int j;
			for(j=0;j<2;j++) {
				if(matrizBotoes[j][i].getIcon()!=null)
					if(matrizBotoes[j][i].getIcon() == matrizBotoes[j+1][i].getIcon())
						contagem++;
			}
			if(contagem==2) {
				//indicando campos que formam o trio
				for(int k=0;k<3;k++) {
					if(matrizBotoes[k][i].getIcon()==Opreto)
						matrizBotoes[k][i].setIcon(Ocolorido);
					else
						matrizBotoes[k][i].setIcon(Xazul);
				}
				exibeGanhador((ImageIcon)matrizBotoes[j-1][i].getIcon());
			}
			contagem = 0;
		}

		//checar se houve ganhador na diagonal 
		if(matrizBotoes[0][0].getIcon()==matrizBotoes[1][1].getIcon() && 
			matrizBotoes[1][1].getIcon()==matrizBotoes[2][2].getIcon() && matrizBotoes[0][0].getIcon()!=null) {
			for(int m=0;m<3;m++){
				if(matrizBotoes[m][m].getIcon()==Opreto)
					matrizBotoes[m][m].setIcon(Ocolorido);
				else
					matrizBotoes[m][m].setIcon(Xazul);
			}
			exibeGanhador((ImageIcon)matrizBotoes[0][0].getIcon());	
		}
		//checar se houve ganhador na outra diagonal
		if(matrizBotoes[0][2].getIcon()==matrizBotoes[1][1].getIcon() && 
			matrizBotoes[1][1].getIcon()==matrizBotoes[2][0].getIcon() && matrizBotoes[0][2].getIcon()!=null) {
			for(int m=0;m<3;m++) {
				if(matrizBotoes[m][2-m].getIcon()==Opreto)
					matrizBotoes[m][2-m].setIcon(Ocolorido);
				else
					matrizBotoes[m][2-m].setIcon(Xazul);
			}
			exibeGanhador((ImageIcon)matrizBotoes[0][2].getIcon());	
		}	
	}
	// mostrar no final do game o jogador que fizer velha primeiro

	public void exibeGanhador(ImageIcon image) {
		if(image==Ocolorido) {
			JOptionPane.showMessageDialog(janela, "O jogador do O venceu o Game!");
			fimDeJogo = true;
		}
		else {
			JOptionPane.showMessageDialog(janela, "O jogador do X venceu o Game!");
			fimDeJogo = true;
		}
	}
	
}
