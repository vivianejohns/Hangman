package hangman;

import java.util.Arrays;
import java.util.Random;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Hangman extends Application {
	
	
	String[] wordcloud = {"Kirschkern","Atmosphaere","Rhythmus","Gymnastik","Metapher","Arbeitsunfaehigkeitsbescheinigung","Haftpflichtversicherung","Zyklop","Weihnachtsmann","Heizoel","Rueckstoss","Abdaempfung","Weitspuckwettbewerb","Umweltschutzorganisation","Finanzdienstleistungsunternehmen","Nahrungsmittel","Eintrittskarte","Schmetterling","Tierschutzverein","Babypuppe","Kopfball","Vollmond","Dachpappe","Fussballweltmeisterschaft","Wasserverschmutzung"};
	Random rnd = new Random();
	Boolean playing;
	int versuche = 0;
	int d = 0;
	
	@Override
	public void start(Stage primaryStage) {
		try {
// Startwindow
					
			primaryStage.setTitle("Lass uns Hangman spielen!");
			BorderPane borderGame = new BorderPane();
			borderGame.setPadding(new Insets(30,20,40,20));
			Scene sceneGame = new Scene(borderGame,400,400);
			
			Label[] asciiHangman = new Label[8];
			asciiHangman[7] = new Label 	("  +---------+  \n" +
											 "  |\t\t  |  \n" +
											 "  |\t\t   \n" +
											 "  |\t\t   O  \n" +
											 "  |\t\t  / | \\\n" +
											 "  |\t\t   / \\\n" +
											 "==========\n");
			asciiHangman[6] = new Label 	("  +---------+  \n" +
											 "  |\t\t  |  \n" +
											 "  |\t\t   \n" +
											 "  |\t\t \n" +
											 "  |\t\t \n" +
											 "  |       \n" +
											 "==========\n");
			asciiHangman[5] = new Label 	("  +---------+  \n" +
											 "  |\t\t  |  \n" +
											 "  |\t\t O  \n" +
											 "  |\t\t  \n" +
											 "  |\t\t \n" +
											 "  |       \n" +
											 "==========\n");
			asciiHangman[4] = new Label 	("  +---------+  \n" +
											 "  |\t\t  |  \n" +
											 "  |\t\t O  \n" +
											 "  |\t\t  |\n" +
											 "  |\t\t \n" +
											 "  |       \n" +
											 "==========\n");
			asciiHangman[3] = new Label 	("  +---------+  \n" +
											 "  |\t\t  |  \n" +
											 "  |\t\t O  \n" +
											 "  |\t\t/ |\n" +
											 "  |\t\t \n" +
											 "  |       \n" +
											 "==========\n");
			asciiHangman[2] = new Label 	("  +---------+  \n" +
											 "  |\t\t  |  \n" +
											 "  |\t\t O  \n" +
											 "  |\t\t/ | \\\n" +
											 "  |\t\t  \n" +
											 "  |       \n" +
											 "==========\n");
			asciiHangman[1] = new Label 	("  +---------+  \n" +
											 "  |\t\t  |  \n" +
											 "  |\t\t O  \n" +
											 "  |\t\t/ | \\\n" +
											 "  |\t\t / \n" +
											 "  |       \n" +
											 "==========\n");
			asciiHangman[0] = new Label 	("  +---------+  \n" +
											 "  |\t\t  |  \n" +
											 "  |\t\t O  \n" +
											 "  |\t\t/ | \\\n" +
											 "  |\t\t / \\\n" +
											 "  |       \n" +
											 "==========\n");
			
			switch (versuche) {
			case 6:
				borderGame.setCenter(asciiHangman[6]);
				break;
			case 5:
				borderGame.setCenter(asciiHangman[5]);
				break;
			case 4:
				borderGame.setCenter(asciiHangman[4]);
				break;
			case 3:
				borderGame.setCenter(asciiHangman[3]);
				break;
			case 2:
				borderGame.setCenter(asciiHangman[2]);
				break;
			case 1:
				borderGame.setCenter(asciiHangman[1]);
				break;
			case 0:
				borderGame.setCenter(asciiHangman[0]);
				break;
			}
			
			String starttext[] = {"Schaffst du es heute zu gewinnen?","Jedes Mal wenn du Hangman spielst, verliert eine Strichmännchen Familie seinen Vater","Warum gibt es keine dicken Strichmännchen?"};
			int rndText = rnd.nextInt(starttext.length);
			Label lbStart = new Label();
			lbStart.setText(starttext[rndText]);
			lbStart.setFont(Font.font("Arial",FontWeight.BOLD,15));
			lbStart.setTextFill(Color.DARKRED);
			lbStart.setWrapText(true);
			lbStart.setTextAlignment(TextAlignment.CENTER);
			borderGame.setAlignment(lbStart, Pos.BOTTOM_CENTER);
			borderGame.setBottom(lbStart);
			borderGame.setCenter(asciiHangman[7]);
			
			Label xFrontEnd = new Label();
			Label xBackEnd = new Label();
			
			Button bNewGame = new Button ("Neues Spiel");
			bNewGame.setOnAction(e -> {
				
				lbStart.setVisible(false);
				asciiHangman[0].setTextFill(Color.BLACK);
				
				int rndWord = rnd.nextInt(wordcloud.length);
				char rndWordLetters[] = wordcloud[rndWord].toUpperCase().toCharArray();
				char playerGuess[] = new char[rndWordLetters.length];
				char inputChars[] = new char[26];
								
				for (int i = 0; i<rndWordLetters.length; i++) {
					playerGuess[i] = '_';
				} 
				updateUserWord(rndWordLetters,playerGuess,xFrontEnd);
				borderGame.setCenter(asciiHangman[6]);
				
				Label lbWrongLetters = new Label("Falsch eingegebene Buchstaben:");
				lbWrongLetters.setTextAlignment(TextAlignment.CENTER);
				Label lbBuchstaben = new Label();
				lbBuchstaben.setText("Das gesuchte Wort hat " + rndWordLetters.length + " Buchstaben");
				Label lbInputChars = new Label();
				
				VBox vbBottom = new VBox(10);				
				vbBottom.setAlignment(Pos.BOTTOM_CENTER);
				vbBottom.getChildren().addAll(xFrontEnd,lbWrongLetters,lbInputChars,lbBuchstaben);
				borderGame.setBottom(vbBottom);
				
				
				VBox vbRight = new VBox(20);
				Label description = new Label("Bitte gib einen \nBuchstaben ein.");
				TextField userInput = new TextField();
				userInput.setPrefWidth(10);
				userInput.textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
						if(arg2.length()>1) {
							userInput.deleteNextChar();
						}
					}
				});
				
				versuche = 6;
				Label lbversuche = new Label("Versuche: "+versuche);
				lbversuche.setTextAlignment(TextAlignment.CENTER);
				lbversuche.setPrefWidth(70);
				

				Button bAbsenden = new Button ("_Absenden");
				bAbsenden.setPrefWidth(85);
				bAbsenden.setOnAction(event -> {
					boolean letterFound = false,wordFound = false, doubleInput = false;
					if(userInput.getText() !=null && !userInput.getText().isEmpty()) {
						userInput.setPromptText("");
						char buchstabe = userInput.getText().toUpperCase().charAt(0);
						
						for(int i = 0; i<playerGuess.length; i++) {	
							if(rndWordLetters[i] == buchstabe) { 								
								letterFound = true;
								playerGuess[i] = buchstabe;	
								break;
							} 
						}
						
						for(int i = 0; i<playerGuess.length; i++) {	
							if(rndWordLetters[i] == buchstabe) { 								
								playerGuess[i] = buchstabe;	
							} 
						}
						
						for(int i = 0; i<inputChars.length;i++) {
							if(buchstabe == inputChars[i]) {
								doubleInput = true;
								inputChars[inputChars.length-1] = ' ';
								userInput.setPromptText("Doppelt: " + buchstabe);
								break;
							}
						}
						
						if(letterFound == false && doubleInput == false) {
							versuche--;
							inputChars[d] = buchstabe;
							d++;
							lbInputChars.setText(lbInputChars.getText()+ " " +buchstabe);
						}
						
						borderGame.setCenter(asciiHangman[versuche]);
						lbversuche.setText("Versuche: "+versuche);
						updateUserWord(rndWordLetters,playerGuess,xFrontEnd);
						updateUserWordBackend(rndWordLetters,playerGuess,xBackEnd);
						userInput.clear();
					} else {
						userInput.setPromptText("Leer!");
					}

					String guess = xBackEnd.getText();
					if(wordcloud[rndWord].toUpperCase().equals(guess)) {
						borderGame.setCenter(asciiHangman[7]);
						asciiHangman[7].setTextFill(Color.GREEN);
						lbBuchstaben.setFont(Font.font("Arial",FontWeight.BOLD,12));
						lbBuchstaben.setText("Herzlichen Glückwunsch, du hast das Wort gefuden. \nDu hast " + d + " mal falsch geraten.");
						lbBuchstaben.setTextAlignment(TextAlignment.CENTER);
						bAbsenden.setDisable(true);
					}
					
					if(versuche==0) {
						asciiHangman[0].setTextFill(Color.RED);
						bAbsenden.setDisable(true);
						for (int i = 0; i<rndWordLetters.length; i++) {
							playerGuess[i] = rndWordLetters[i];
						} 
						updateUserWord(rndWordLetters,playerGuess,xFrontEnd);
						lbBuchstaben.setFont(Font.font("Arial",FontWeight.BOLD,12));
						lbBuchstaben.setText("Deine Versuche sind aufgebraucht. \nDu hast das Wort leider nicht gefunden.");
						lbBuchstaben.setTextAlignment(TextAlignment.CENTER);
					}
//					userInput.requestFocus();			// prompttext wird dann nicht mehr angezeigt
				}); 
				vbRight.setAlignment(Pos.CENTER_RIGHT);
				vbRight.getChildren().addAll(description,userInput,bAbsenden,lbversuche);
				
				borderGame.setRight(vbRight);	
			});
			
			
			borderGame.setAlignment(bNewGame, Pos.TOP_CENTER);
			borderGame.setTop(bNewGame);
			primaryStage.setScene(sceneGame);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}	
			
	}

// Frontend + Backend, weil Frontend mit Leerzeichen dazwischen, damit besser lesbar für Nutzer
	public static void updateUserWord (char letters[], char guess[], Label x) {
		String string = "";
		for (int i = 0; i<letters.length; i++) {
			string += guess[i] + " ";
			x.setText(string);
		}
		
	}
	public static void updateUserWordBackend (char letters[], char guess[], Label x) {
		String string = "";
		for (int i = 0; i<letters.length; i++) {
			string += guess[i];
			x.setText(string);
		}
		
	}
	
	public static void main(String[] args) {
			launch(args);
		}
	}
