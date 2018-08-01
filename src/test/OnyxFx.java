/*
=================================================================================
LICENSE: GNU GPL V2 (https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html)

OnyxFX, an app to query NBA™ statistical data.
Copyright (C) <2018>  ADRIAN D. FINLAY.

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.

Adrian D. Finlay, hereby disclaims all copyright interest in the program
`OnyxFX' (which makes passes at compilers) written by Oracle Corporation.

Adrian D. Finlay, July 29, 2018
Adrian D. Finlay, Founder
www.adriandavid.me
Contact: adf5152@live.com

NBA™ is a registered trademark of the National Basketball Association.
All rights reserved.

=================================================================================
**/


// package me.adriandavid;

import java.net.URL;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;
import java.io.IOException;
import javafx.geometry.Pos;
import java.io.BufferedReader;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.io.InputStreamReader;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.util.function.BiConsumer;
import java.net.HttpURLConnection;
import javafx.scene.text.FontWeight;
import java.util.function.Predicate;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundSize;

public class OnyxFx extends Application {

	/*  Connect to the API first. Gives heroku fre tier sometime to activate dynos / JVM. */
	private static final String OnyxFxAPI = "https://onyxfx-api.herokuapp.com/nbaBasicStatBean?";
	static { 
		try { new URL(OnyxFxAPI).openConnection().connect(); }
		catch (Exception e) { ; }; //ignore exception
	}
	//Containers, Components, Primitives, Etc.
	private Image app, splash;
	private ImageView bkg, calc, clear;
	static TextField firstn_ans, surname_ans, season_ans;

	//Visual Bounds of target environment
	private static final double height = Screen.getPrimary().getVisualBounds().getHeight();
	private static final double width = Screen.getPrimary().getVisualBounds().getWidth();

	/* Default NBA Player: HTTP GET-> https://onyxfx-api.herokuapp.com/nbaBasicStatBean?firstName=Dwyane&surname=Wade&season=2008 */
	private static NBAPlayer player = NBAPlayer.getPlayer("Dwyane", "Wade", 2008);
	
	//NBA Player StatLine
	Map<String,String> statLine;


	//NBAPlayer Class
	static class NBAPlayer {

		// Properties
		private Map<String, String> playerInfo;
		private Map<String, String> statLine;
		private static NBAPlayer player;
		private HttpURLConnection OnyxFxAPI;
		private BufferedReader reader;
		private List lastEntry;
		
		// Constructor
		private NBAPlayer (String firstName, String lastName, int season) {
			this.playerInfo = new HashMap<String, String> (3);
			this.playerInfo.put("firstName", firstName);
			this.playerInfo.put("lastName", lastName);
			this.playerInfo.put("season", String.valueOf(season));	
			this.statLine = new LinkedHashMap<String, String> (3);	
			this.lastEntry = Arrays.asList(firstName, lastName, season);
		};

		// Methods
		static NBAPlayer getPlayer(String firstName, String lastName, int season) { 
			if (player == null)
				NBAPlayer.player = new NBAPlayer (firstName, lastName, season);
			else 
				throw new UnsupportedOperationException ("This class is a singleton.");
			return player;
		};
		NBAPlayer setPlayer(String firstName, String lastName, int season) { 
			this.playerInfo.replace("firstName", firstName);
			this.playerInfo.replace("lastName", lastName);
			this.playerInfo.replace("season", String.valueOf(season));
			return player;
		};
		private String getRESTString (String api) {
			 return new StringBuilder(api)
			 			.append("firstName=")
			 			.append(this.playerInfo.get("firstName"))
			 			.append("&surname=")
			 			.append(this.playerInfo.get("lastName"))
			 			.append("&season=")
			 			.append(this.playerInfo.get("season"))
		 			.toString();
		};
		private boolean verify (String OnyxFxAPI) {
			try {
				this.OnyxFxAPI = (HttpURLConnection) ( new URL(getRESTString(OnyxFxAPI)) ).openConnection();
				this.OnyxFxAPI.setRequestMethod("GET");
				this.OnyxFxAPI.setRequestProperty("Accept", "application/json");
				if (this.OnyxFxAPI.getResponseCode() != 200)
					throw new RuntimeException();
			} catch (Exception e) {	return false; }
			return true;
		};
		private Map<String, String> getStatLine(Predicate<String> T, String apiURL) {
			try {
				if (T.test(apiURL)) {
					if (  OnyxFx.firstn_ans != null &&
						  OnyxFx.firstn_ans.getText().equals(lastEntry.get(0)) &&
						  OnyxFx.surname_ans.getText().equals(lastEntry.get(1)) &&
						  OnyxFx.season_ans.getText().equals(lastEntry.get(2)) 
					) {  System.out.println("hello"); return this.statLine; 		}
					else {

						this.lastEntry = Arrays.asList(OnyxFx.firstn_ans, OnyxFx.surname_ans, OnyxFx.season_ans);

						//parsing json is definetly best handled by various 3rd party libraries
						//but suffice for now.
						reader = new BufferedReader(new InputStreamReader(OnyxFxAPI.getInputStream()));
						StringBuilder json_sb = new StringBuilder();
						String element ="";
						while (	(element = reader.readLine()) != null ) {
							json_sb.append(element);
						}

						String json = json_sb.toString();

						if (json.contains("-1")) {

							this.playerInfo.replace("firstName", "Invalid Entry");
							this.playerInfo.replace("lastName", "Invalid Entry");
							this.playerInfo.replace("season", "Invalid Entry");
							
							OnyxFx.firstn_ans.setText(this.playerInfo.get("firstName"));
							OnyxFx.surname_ans.setText(this.playerInfo.get("lastName"));
							OnyxFx.season_ans.setText(this.playerInfo.get("season"));
							
							this.statLine.put("PPG", "---");
							this.statLine.put("RPG", "---");
							this.statLine.put("APG", "---");
							return this.statLine;
						}
						else {
							
							String PPG, RPG, APG;
							String[] PG = new String[] {
								json.substring(json.indexOf("ppg\":") + "ppg\":".length(), json.length()),
								json.substring (json.indexOf("rpg\":") + "rpg\":".length(), json.length()),
								json.substring (json.indexOf("apg\":") + "apg\":".length(), json.length())
							};

							//PPG
							if (PG[0].contains(","))
								PPG = PG[0].substring(0, PG[0].indexOf(","));
							else
								PPG = PG[0].substring(0, PG[0].length()-1);

							//RPG
							if (PG[1].contains(","))
								RPG = PG[1].substring(0, PG[1].indexOf(","));
							else
								RPG = PG[1].substring(0, PG[1].length()-1);

							//APG
							if (PG[2].contains(","))
								APG = PG[2].substring(0, PG[2].indexOf(","));
							else
								APG = PG[2].substring(0, PG[2].length()-1);

							this.statLine.put("PPG", PPG);
							this.statLine.put("RPG", RPG);
							this.statLine.put("APG", APG);
							return this.statLine;
						}
					}
				} 
				else 
					return this.statLine;
			} catch (IOException ioe) {
				this.playerInfo.replace("firstName", "Invalid Entry");
				this.playerInfo.replace("lastName", "Invalid Entry");
				this.playerInfo.replace("season", "Invalid Entry");
				this.statLine.put("PPG", "---");
				this.statLine.put("RPG", "---");
				this.statLine.put("APG", "---");
				return this.statLine;
			}
		};
		HttpURLConnection getHUCInstance() { return OnyxFxAPI; };
		Map<String, String> getPlayerInfo() { return playerInfo; };
	}

	// JavaFX Lifecycle Method #1
	@Override public void init () { 
		try {
			app = new Image(getClass().getResource("/me/adriandavid/images/basketball.png").toExternalForm());
			bkg = new ImageView(new Image(getClass().getResource("/me/adriandavid/images/wade2.png").toExternalForm()));
			splash = new Image(getClass().getResource("/me/adriandavid/images/wade_dunk.png").toExternalForm());
		} catch (NullPointerException exc) {  System.out.println("Log: One or more resources could not be found.\n" + exc); System.exit(0);	}
	};

    @Override // JavaFX Lifecycle Method #2
    public void start(Stage main) {

		/* Splash Screen*/
		SplashScreen.render(new double[] { OnyxFx.height, OnyxFx.width } , app, splash).showAndWait();	

    	//Get Default NBA Player StatLine
    	statLine = OnyxFx.player.getStatLine(OnyxFx.player::verify, OnyxFx.OnyxFxAPI);

    	//Consumer for map iteration
    	BiConsumer<String,String> bic = (k,v) ->  {} ;

		/* Main Screen */
        //Give the stage a title.
		main.setTitle("OnyxFX - NBA® Stat Tracker");

		// Provide Icon
		main.getIcons().add(app);

		//Root Node
		VBox rootNode = new VBox(3.0);
		rootNode.setAlignment(Pos.CENTER);

		//UI Components
		Font font = Font.font("Franklin Gothic Book", FontWeight.EXTRA_BOLD, 20);

		Label title = new Label ("OnyxFx");
		title.setFont(font);
		
		Label firstn = new Label ("First Name:");
		firstn.setFont(font);
		firstn_ans = new TextField (player.getPlayerInfo().get("firstName"));

		Label surname = new Label ("Surname:");
		surname.setFont(font);
		surname_ans = new TextField (player.getPlayerInfo().get("lastName"));

		Label season = new Label ("Season:");
		season.setFont(font);
		season_ans = new TextField (player.getPlayerInfo().get("season"));

		Label ppg = new Label ("PPG");
		ppg.setFont(font);
		TextField ppg_ans = new TextField (statLine.get("PPG"));
		ppg_ans.setEditable(false);

		Label rpg = new Label ("RPG");
		rpg.setFont(font);
		TextField rpg_ans = new TextField (statLine.get("RPG"));
		rpg_ans.setEditable(false);

		Label apg = new Label ("APG");
		apg.setFont(font);
		TextField apg_ans = new TextField (statLine.get("APG"));
		apg_ans.setEditable(false);

		VBox titleh = new VBox(title);
		VBox entry_label = new VBox(firstn,surname,season);
		VBox entry_text = new VBox(firstn_ans,surname_ans,season_ans);
		HBox entry = new HBox(entry_label, entry_text);

		VBox ppgv = new VBox(ppg, ppg_ans);
		VBox rpgv = new VBox(rpg, rpg_ans);
		VBox apgv = new VBox(apg, apg_ans);
		HBox rsp = new HBox(ppgv, rpgv, apgv);
		
		List<TextField> rsps = Arrays.asList(ppg_ans, rpg_ans, apg_ans); //Fixed Size List
		
		titleh.setAlignment(Pos.CENTER);
		entry.setAlignment(Pos.CENTER);
		rsp.setAlignment(Pos.CENTER);

		TextField[] textfs = new TextField[] { firstn_ans, surname_ans, season_ans };

		bkg.setPreserveRatio(true);
		bkg.setFitHeight(OnyxFx.height*.60);
		
		//Components -> SceneGraph
		rootNode.getChildren().addAll(titleh,entry,bkg,rsp);
		rootNode.setAlignment(Pos.CENTER);
		rootNode.setPadding(new Insets(1));

		//Set a background image
		Background background = new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY));

		//Background -> Root Node
		rootNode.setBackground(background);

        //Scene
        Scene scene = new Scene(rootNode, 325, 610); //OnyxFx.width, OnyxFx.height);


        /// Event Handling
        firstn_ans.setOnAction((e) -> {
       		for (TextField tf : textfs) {
    			if (tf.getText().trim().isEmpty())
       				return; //suffice for now to ignore incomplete queries
    		}

        	statLine = player.setPlayer( firstn_ans.getText().trim(), surname_ans.getText().trim(), Integer.valueOf(season_ans.getText().trim()))
        		.getStatLine(OnyxFx.player::verify, OnyxFx.OnyxFxAPI);
			
			java.util.ListIterator it = rsps.listIterator();
    		statLine.forEach(bic.andThen( (k,v) -> ((TextField)(it.next())).setText(v) ) );
        	} 
        );
        surname_ans.setOnAction((e) -> {
    		for (TextField tf : textfs) {
    			if (tf.getText().trim().isEmpty())
    				return; //suffice for now to ignore incomplete queries
    		}

        	statLine = player.setPlayer( firstn_ans.getText().trim(), surname_ans.getText().trim(), Integer.valueOf(season_ans.getText().trim()))
        		.getStatLine(OnyxFx.player::verify, OnyxFx.OnyxFxAPI);

			java.util.ListIterator it = rsps.listIterator();
    		statLine.forEach(bic.andThen( (k,v) -> ((TextField)(it.next())).setText(v) ) );

        	} 
        );
        season_ans.setOnAction((e) -> {
    		for (TextField tf : textfs) {
    			if (tf.getText().trim().isEmpty())
        			return; //suffice for now to ignore incomplete queries
    		}

     		statLine = player.setPlayer( firstn_ans.getText().trim(), surname_ans.getText().trim(), Integer.valueOf(season_ans.getText().trim()))
    			.getStatLine(OnyxFx.player::verify, OnyxFx.OnyxFxAPI);

			java.util.ListIterator it = rsps.listIterator();
    		statLine.forEach(bic.andThen( (k,v) -> ((TextField)(it.next())).setText(v) ) );

        	} 
        );

   		//Restrict resizeable
		main.setResizable(false);

        //Set Scene, Show stage
        main.setScene(scene);
        main.show();
    }

    // JavaFX Lifecycle Method #3
    @Override public void stop () { player.getHUCInstance().disconnect(); };
}