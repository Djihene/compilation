package compilat;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class compilati {

	JFrame frmMonAnalyseur;
	JTextArea textArea;

	static JFileChooser file_chooser = new JFileChooser("C:\\Users\\sol\\Desktop");
	static FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers text", "COMPILA");
	static ArrayList<String> words = new ArrayList<String>();
	static ArrayList<String> lignes = new ArrayList<String>();
	static ArrayList<String> sortie_lexic = new ArrayList<String>();
	static String[] word;

/*********************************************************************************************************************/
	public static void charger() throws FileNotFoundException {
		file_chooser.addChoosableFileFilter(filter);
		if(file_chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
			File file = file_chooser.getSelectedFile();
			Scanner sc_lignes = new Scanner(file);
			Scanner sc_mots = new Scanner(file);
			words.clear();
			lignes.clear();
				while(sc_lignes.hasNextLine()){
					lignes.add(sc_lignes.nextLine());
				}
				while(sc_mots.hasNext()){
					words.add(sc_mots.next());
					}

			sc_mots.close();
			sc_lignes.close();
			}
	}

/*********************************************************************************************************************/

	public boolean isNum(String chaine, int i) {
		char[] nombre = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
		int j = 0;
		while (j < nombre.length) {
			if (chaine.charAt(i) == nombre[j]) {
				return true;
			}
			j++;
		}

		return false;
	}

	public String num(String chaine) {
		int i = 0;
		int token_pos = 0;
		boolean point_unique = true;
		while (i < chaine.length()) {
			if (isNum(chaine, i)) token_pos++;
			else if(point_unique & chaine.charAt(token_pos)==',') {
				token_pos++;
				point_unique = false;
			}
			i++;
		}

		if (token_pos == chaine.length() && !chaine.contains(",")) return "Nombre entier";
		else if (token_pos == chaine.length() && !point_unique) return "Nombre reel";
		return null;

	}

/*********************************************************************************************************************/

	public boolean isChar(String chaine, int i) {
		char[] alphabet = { 'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e', 'F', 'f', 'G', 'g', 'H', 'h', 'I', 'i',
				'J', 'j', 'K', 'k', 'L', 'l', 'M', 'm', 'N', 'n', 'O', 'o', 'P', 'p', 'Q', 'q', 'R', 'r', 'S', 's', 'T',
				't', 'U', 'u', 'V', 'v', 'W', 'w', 'X', 'x', 'Y', 'y', 'Z', 'z' };
		int k = 0;
		while (k < alphabet.length) {
			if (chaine.charAt(i) == alphabet[k]) {
				return true;
			}
			k++;
		}
		return false;

	}

	public String id(String chaine) {
		boolean verifier_Premier = false;
		boolean tiret_unique = true;
		int token_pos = 0;
		int i = 0;
		if (isChar(chaine, 0)) {
			token_pos++;
			verifier_Premier = true;
		}
		if (verifier_Premier == true && chaine.length() == 1)
			return "identificateur";

		else if (chaine.length() > 1) {
			i = 1;
			while (i < chaine.length()) {

				if (isChar(chaine, i))
					{token_pos++;
					tiret_unique=true;
					}
				else if (isNum(chaine, i))
					{token_pos++;
					tiret_unique=true;
					}
				else if (chaine.charAt(i) == '_' && tiret_unique) {
					tiret_unique=true;
					token_pos++;
				}
				i++;
			}
			if (token_pos == chaine.length())
				return "Chaine de carectere";
		}
		return null;
	}

/*********************************************************************************************************************/

	public String UL_reserve(String chaine) {
		
		String[] mot_reserve = {"\"", "<", ">", ",", "Start_Program", "Int_Number", ";;", "Give", "Real_Number", "If", "--", "Else",
				"Start", "Affect","to", "Finish", "ShowMes","ShowVal", "//.",":", "End_Program" };

		String[] Affichage = {"mot reserve pour guillemets",
				"inferieur", "superieur", "caractere reserve virgule",
				"Mot reserve debut du programme", " Mot reserve pour declaration d'un entier",
				"Mot reserve fin instruction", "Mot reserve pour affectation entre variables", " Mot reserve debut declaration d'un Real",
				" Mot reserve pour condition SI", "Mot reserve pour condition", "Mot reserve pour condition SINON", "Debut d'un sous programme",
				"Mot reserve pour affectation", "Mot reserve pour affectation", "Fin d'un sous programme",
				"Mot reserve pour afficher un message ","mot reservé pour l'affichage de valeur de variable ", "carectere reserve pour un commentaire","carectere reserve", "Mot reserve Fin du programme" };
		int i = 0;
		while (i < mot_reserve.length) {
			if (chaine.equals(mot_reserve[i])) {
				return Affichage[i];
			}
			i++;
		}
		return null;
	}

/*********************************************************************************************************************/

	public String syntax(String chaine){
	
		if(chaine.equals("Start_Program")) return "debut de programme";
		else if(chaine.equals("Else")) return "else";
		else if(chaine.equals("Start")) return "Debut d'un bloc";
		else if(chaine.equals("Finish")) return "Fin d'un bloc";
		else if(chaine.startsWith("//.")) return "c'est un commentaire";
		else if(chaine.equals("End_Program")) return "Fin du programme";
		else if(chaine.startsWith("ShowMes : \" " ) && chaine.endsWith(" \" ;;")) return "c'est une chaine de carectere";
		else if(chaine.contains(" ")) {
			word = chaine.split(" ");
			int i=0, k=1;

				if(word[i].equals("Int_Number")){
					i++;
					if(word[i].equals(" "))
						i++;
                                        if(word[i].equals(":"))
						i++;
                                        if(word[i].equals(" "))
						i++;
						if(id(word[i]) != null){
							i++;
							while(word[i].equals(",")){
								i++;
								k++;
								if(id(word[i]) != null) i++;
							}
							if(word[i].equals(";;")) return "Declaration de "+k+" variables entiers";
						}
					

				}
				else if(word[i].equals("Give")){
					i++;
					if(id(word[i]) != null){
						i++;
					if(word[i].equals(" "))i++;
                                        if(word[i].equals(":"))i++;
                                        if(word[i].equals(" "))i++;
						if(num(word[i]) == "Nombre entier") {
							i++;
							if(word[i].equals(";;")) return "affectation dune valeur entiere a la variable : "+word[i-3];
						}
						else if(num(word[i]) == "Nombre reel"){
							i++;
							if(word[i].equals(";;")) return "affectation dune valeur reel a la variable : "+word[i-4];
						}

					
				}

				}
				
				else if(word[i].equals("Real_Number")){
					i++;
					if(word[i].equals(" "))i++;
                                            if(word[i].equals(":"))i++;
						if(id(word[i]) != null)i++;
                                                   if(word[i].equals(" "))i++;
							if(word[i].equals(";;")) return "Declaration de  variable reel";
						}


				
				
				else if(word[i].equals("If")){
					i++;
					if(word[i].equals("--")){
						i++;
					if(id(word[i]) != null){
						i++;
						if(word[i].equals("<") || word[i].equals(">") || word[i].equals("==")){
						i++;
						if(id(word[i]) != null){
							i++;
						if(word[i].equals("--")) return "condition"; 
							}}}}
				}
				
				
				
				else if(word[i].equals("Affect")){
					i++;
					if(id(word[i]) != null){
						i++;
                                           if(word[i].equals(" "))i++;
					if(word[i].equals("to")){
						i++;
                                                if(word[i].equals(" "))i++;
						if(id(word[i]) != null) {
							i++;
							if(word[i].equals(";;")) return "affectation d'une variable a une variable";
						}

					}

				}

				}
				
				
				else if(word[i].equals("ShowMes")){
					i++;
					if(word[i].equals(" "))i++;
                                            if(word[i].equals(":"))i++;
                                              if(word[i].equals(" "))i++;
                                              if(word[i].equals("\""))i++;
						if(id(word[i]) != null)i++;
                                                  if(word[i].equals("\""))i++;
							if(word[i].equals(";;")) return "affichage d'un message a l'ecran ";
						}


				
								
		else if(word[i].equals("ShowVal")){
			i++;
			if(word[i].equals(" "))i++;
                                    if(word[i].equals(":"))i++;
                                      if(word[i].equals(" "))i++;
                                   
				if(id(word[i]) != null)i++;
				 if(word[i].equals(" "))i++;
					if(word[i].equals(";;")) return "affichage de la valeur d'une variable ";
				}
		}

		
		return "erreur de syntaxe";
	}

	
	
/*********************************************************************************************************************/


	public void lexicale(List<String> liste) {
		int i = 0;

		while (i < words.size()) {
			if (UL_reserve(words.get(i)) != null) {
				sortie_lexic.add(UL_reserve(words.get(i)));
			} else if (id(words.get(i)) != null) {
				sortie_lexic.add(id(words.get(i)));
			} else if (num(words.get(i)) != null) {
				sortie_lexic.add(num(words.get(i)));
			}
			else sortie_lexic.add("Erreur");

			i++;
		}

	}

/*********************************************************************************************************************/
	
	public String semantique(String chaine){
		if(chaine.equals("Start_Program")) return "Debut du Program";
		else if(chaine.equals("Else")) return "Sinon";
		else if(chaine.equals("Start")) return "Debut du bloc";
		else if(chaine.equals("Finish")) return "Fin du bloc";
		else if(chaine.startsWith("//.")) return "Exemple d'un commentaire";
		else if(chaine.equals("End_Program")) return "Fin du Program";
		else if(chaine.contains(" ")) {
			word = chaine.split(" ");
			int i=0;

				if(word[i].equals("Int_Number")){
					i++;
					if(word[i].equals(" "))
						i++;
                                        if(word[i].equals(":"))
						i++;
                                        if(word[i].equals(" "))
						i++;
						if(id(word[i]) != null){
							i++;
							while(word[i].equals(",")){
								i++;
								if(id(word[i]) != null) i++;
                                                                if(word[i].equals(";;"))return "Declaration de la variable "+" "+word[i-1]+","+word[i-2]+";";
							}
							if(word[i].equals(";;"))return "Declaration de la variable "+" "+word[i-1]+";";
						}
					

				}
				
				else if(word[i].equals("Give")){
					i++;
                                        if(word[i].equals(" "))i++;
                                       
                                       
					if(id(word[i]) != null){
						i++;
                                                if(word[i].equals(":"))i++;
                                          if(word[i].equals(" "))i++;       
					if(num(word[i]) == "Nombre entier") {
							i++;
                                                        if(word[i].equals(" "))i++;
							if(word[i].equals(";;")) return "Affectation de la valeur entiere  "+word[i-1]+"  à la variable  "+word[i-3];
						}
						else if(num(word[i]) == "Nombre reel"){
							i++;
                                                        if(word[i].equals(" "))i++;
							if(word[i].equals(";;")) return "Affectation de la valeur reél : "+word[i-1]+" à la variable : "+word[i-3];
						}

					
				}

				}
				
				else if(word[i].equals("Real_Number")){
					i++;
					if(word[i].equals(" "))i++;
                                        if(word[i].equals(":"))i++;
                                        if(word[i].equals(" "))i++;
						if(id(word[i]) != null)i++;
							if(word[i].equals(";;")) return " c'est une déclaration d'une variable reel"+word[i-1];
						}


				
				
				else if(word[i].equals("If")){
					i++;
					if(word[i].equals("--")){
						i++;
					if(id(word[i]) != null){
						i++;
						if(word[i].equals("<") || word[i].equals(">") || word[i].equals("==")){
						i++;
						if(id(word[i]) != null){
							i++;
						if(word[i].equals("--")) return "Condition Si ("+word[i-3]+word[i-2]+word[i-1]+") alors action"; 
							}}}}
				}
				
				
				
				else if(word[i].equals("Affect")){
					i++;
					if(id(word[i]) != null){
						i++;
					if(word[i].equals("to")){
						i++;
						if(id(word[i]) != null) {
							i++;
							if(word[i].equals(";;")) return "Affectation de la variable "+word[i-3]+" à "+word[i-1];
						}

					}

				}

				}
				
				
				else if(word[i].equals("ShowMes")){
					i++;
                                        if(word[i].equals(" "))i++;
                                          if(word[i].equals(":"))i++;  
                                          if(word[i].equals(" "))i++;
                                        if(word[i].equals("\""))i++;
                                          if(word[i].equals(" "))i++;
						if(id(word[i]) != null)i++;
                                                if(word[i].equals("\""))i++;
                                                  if(word[i].equals(" "))i++;
							if(word[i].equals(";;")) return "affichage du la chaine de carectere suivante"+word[i-2];
						}

				else if(word[i].equals("ShowVal")){
					i++;
                                        if(word[i].equals(" "))i++;
                                          if(word[i].equals(":"))i++;  
                                          if(word[i].equals(" "))i++;
                                       
                                         
						if(id(word[i]) != null)i++;
                                               
                                                  if(word[i].equals(" "))i++;
							if(word[i].equals(";;")) return "affichage du la valeur  suivante"+word[i-2];
						}

				
				

				
								}
		return "erreur symantique";
		
	}
	
	
	/*********************************************************************************************************************/
	

	 
	 
	/**
	 * launch of the l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					compilati window = new compilati();
					window.frmMonAnalyseur.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public compilati() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMonAnalyseur = new JFrame();
		frmMonAnalyseur.setResizable(true);
		frmMonAnalyseur.setTitle("Analyseur lexicale , syntaxique et Symentique");
		frmMonAnalyseur.getContentPane().setBackground(Color.cyan);
		frmMonAnalyseur.getContentPane().setLayout(null);
		frmMonAnalyseur.setLocationRelativeTo(null);

		Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(245, 245, 245));
		panel.setBounds(20, 50, 300, 550);
		frmMonAnalyseur.getContentPane().add(panel);

				JButton btnNewButton = new JButton("Charger un Fichier");
				btnNewButton.setCursor(cursor);
				btnNewButton.setBounds(20, 81, 195, 59);
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						try {
							  textArea.setText("");
							charger();


							int i = 0;
							while (i < lignes.size()) {
								textArea.setText(textArea.getText()+lignes.get(i)+"\n");
								i++;}
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				});
				panel.setLayout(null);
				btnNewButton.setForeground(Color.BLACK);
				btnNewButton.setBackground(UIManager.getColor("Button.foreground"));
				btnNewButton.setFont(new Font("Roboto", Font.BOLD, 17));
				panel.add(btnNewButton);

				JButton btnAlexicale = new JButton("Analyse Lexicale");
				btnAlexicale.setCursor(cursor);
				btnAlexicale.setBounds(20, 151, 195, 59);
				btnAlexicale.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						textArea.setText("");
						lexicale(words);
						int i = 0;
						while (i < words.size()) {
							textArea.setText(textArea.getText()+words.get(i) + "__________" + sortie_lexic.get(i)+"\n");
							i++;}
					}
				});
				btnAlexicale.setForeground(Color.BLACK);
				btnAlexicale.setBackground(UIManager.getColor("Button.foreground"));
				btnAlexicale.setFont(new Font("Roboto", Font.BOLD, 17));
				panel.add(btnAlexicale);

				JButton btnAsyntaxique = new JButton("Analyse Syntaxique");
				btnAsyntaxique.setCursor(cursor);
				btnAsyntaxique.setBounds(20, 221, 195, 59);
				btnAsyntaxique.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						textArea.setText("");
						int i = 0;
						while (i < lignes.size()) {
							textArea.setText(textArea.getText()+lignes.get(i) + " _______ " +syntax(lignes.get(i))+"\n");
							i++;}
					}
				});
				btnAsyntaxique.setForeground(Color.BLACK);
				btnAsyntaxique.setBackground(UIManager.getColor("Button.foreground"));
				btnAsyntaxique.setFont(new Font("Roboto", Font.BOLD, 17));
				panel.add(btnAsyntaxique);

				JButton btnAsmantique = new JButton("Analyse Semantique");
				btnAsmantique.setCursor(cursor);
				btnAsmantique.setBounds(20, 292, 195, 59);
				btnAsmantique.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						textArea.setText("");
						int i = 0;
						
						
						while (i < lignes.size()) {
							textArea.setText(textArea.getText()+lignes.get(i) + "___________" +semantique(lignes.get(i))+"\n");
							
							i++;}
						
						i=0;
					
						
					}
				
				
				
				
				});
				btnAsmantique.setForeground(Color.black);
				btnAsmantique.setBackground(UIManager.getColor("Button.foreground"));
				btnAsmantique.setFont(new Font("Roboto", Font.BOLD, 17));
				panel.add(btnAsmantique);

				JLabel lblNewLabel = new JLabel("Commandes :");
				lblNewLabel.setBounds(10, 11, 195, 59);
				lblNewLabel.setForeground(Color.BLACK);
				lblNewLabel.setFont(new Font("Roboto", Font.BOLD, 25));
				panel.add(lblNewLabel);

		

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(400, 50, 600, 600);
		frmMonAnalyseur.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 11, 570, 570);
		panel_1.add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		textArea.setForeground(Color.white);
		textArea.setBackground(Color.black);
		textArea.setFont(new Font("Perpetua", Font.BOLD, 16));
		frmMonAnalyseur.setBounds(100, 100, 535, 438);
		frmMonAnalyseur.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

