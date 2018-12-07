# ParallaxFX
ParallaxFX brings parallax effects to JavaFX

## How to Use

```java
public class ParallaxFxDemo extends Application
{

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setTitle("ParallaxFxDemo");
		
		//create a new ParallaxPane
		ParallaxPane pPane = new ParallaxPane();
		
		//imageviews as layers
		ImageView layer8 = new ImageView(new Image(ParallaxFxDemo.class.getResourceAsStream("/layer_08_2048 x 1546.png")));
		ImageView layer7 = new ImageView(new Image(ParallaxFxDemo.class.getResourceAsStream("/layer_07_2048 x 1546.png")));
		ImageView layer6 = new ImageView(new Image(ParallaxFxDemo.class.getResourceAsStream("/layer_06_2048 x 1546.png")));
		ImageView layer5 = new ImageView(new Image(ParallaxFxDemo.class.getResourceAsStream("/layer_05_2048 x 1546.png")));
		ImageView layer4 = new ImageView(new Image(ParallaxFxDemo.class.getResourceAsStream("/layer_04_2048 x 1546.png")));
		ImageView layer3 = new ImageView(new Image(ParallaxFxDemo.class.getResourceAsStream("/layer_03_2048 x 1546.png")));
		ImageView layer2 = new ImageView(new Image(ParallaxFxDemo.class.getResourceAsStream("/layer_02_2048 x 1546.png")));
		ImageView layer1 = new ImageView(new Image(ParallaxFxDemo.class.getResourceAsStream("/layer_01_2048 x 1546.png")));
		
		//add the layers to the pane (order: back-> front)
		pPane.addLayer(layer8, .2d, new Movement(0d, 0d, 0d, 0d));
		pPane.addLayer(layer7, .4d, new Movement(40,40,0d,40));
		pPane.addLayer(layer6, .6d, new Movement(50,50,0d,50));
		pPane.addLayer(layer5, .8d, new Movement(60,60,0d,60));
		pPane.addLayer(layer4, 1.0d, new Movement(70,70,0d,70));
		pPane.addLayer(layer3, 1.2d, new Movement(80,80,0d,80));
		pPane.addLayer(layer2, 1.4d, new Movement(90,90,0d,90));
		pPane.addLayer(layer1, 1.6d, new Movement(200,200,0d,200));
		
		Scene scene = new Scene(pPane, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		//and add the desired effects
		pPane.addMouseScrollParallaxEffect(); //effect while scrolling
		pPane.addMouseMovedParallaxEffect(); //effect while mouse moving
		//pPane.addMouseDraggedParallaxEffect(); //effect while mouse dragging
		pPane.addMouseExitedAnimation(); //animation effect when the mouse leaves the pane
		
		//instead of adding the listener you can also start the return-animation with the following method
		//pPane.animateLayersToOriginalPosition(2000);
	}

	public static void main(String[] args)
	{
		launch(args);
	}

}
```
