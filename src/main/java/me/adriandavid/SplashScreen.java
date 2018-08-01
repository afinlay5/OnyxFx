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

package me.adriandavid;

import java.io.File;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Background;
import javafx.animation.FadeTransition;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundSize;

class SplashScreen {

  private SplashScreen() { }

  public static Stage render (double[] dimensions, Image ... img) {
    /* Splash Screen*/
    Stage splash = new Stage();

    //Add icon
    splash.getIcons().add(img[0]);

    //Root Node
    StackPane splashRootNode = new StackPane(new ImageView(img[1]));
    //Set a background image
    Background background = new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY));

    //Background -> Root Node
    splashRootNode.setBackground(background);

    FadeTransition inAnim = new FadeTransition(Duration.seconds(1.5), splashRootNode);
    inAnim.setFromValue(0);
    inAnim.setToValue(1);
    inAnim.setCycleCount(1);

    FadeTransition outAnim = new FadeTransition(Duration.seconds(1.5), splashRootNode);
    outAnim.setFromValue(1);
    outAnim.setToValue(0);
    outAnim.setCycleCount(1);

    splash.setScene(new Scene(splashRootNode, 480, 615));

    inAnim.play();
    inAnim.setOnFinished((e) -> outAnim.play() );
    outAnim.setOnFinished((e) -> splash.close() );	

    return splash;
  }
}