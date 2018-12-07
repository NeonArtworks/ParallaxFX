package at.neonartworks.parallaxfx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * <h1>ParallaxPane</h1> <br>
 * The ParallaxPane extends a standard javafx {@link AnchorPane}.
 * 
 * @author Florian Wagner
 *
 */
public class ParallaxPane extends AnchorPane
{

	private Map<Node, ParallaxEffect> layersParallaxMap = new HashMap<Node, ParallaxEffect>();
	private Map<Node, DefaultPosition> defaultPositionMap = new HashMap<Node, DefaultPosition>();
	private List<Node> _layersList;
	private double defaultParallaxStrength = 1d;
	private Movement defaultMovement = new Movement(100, 100, 100, 100);
	private ParallaxEffect defaultParallaxEffect = new ParallaxEffect(defaultParallaxStrength, defaultMovement);
	private Delta delta = new Delta();
	private double mousePosX;
	private double mousePosY;
	private double mouseOldX;
	private double mouseOldY;
	private double scrollMultiplier = 10;

	private int cnt;

	/**
	 * Constructor setting the default parallax strength and default maximum
	 * movement. The maxMovement restricts to let a layer go further than its
	 * position + movement (in each direction)
	 * 
	 * @param defautlParallaxStrength the default parallax strength
	 * @param defaultMovement         the default movement
	 */
	public ParallaxPane(double defautlParallaxStrength, Movement defaultMovement)
	{
		this.defaultParallaxStrength = defautlParallaxStrength;
		this.defaultMovement = defaultMovement;
	}

	/**
	 * Constructor.
	 */
	public ParallaxPane()
	{

	}

	/**
	 * Adds a new layer to the parallax pane. Layers are stacked on top of each
	 * other, meaning the first layer will be the farthest in the back.
	 * 
	 * @param layer            the layer (can be any JavaFX node) to add the the
	 *                         parallax pane
	 * @param parallaxStrength the strength of the parallax effect
	 * @param maxMovement      the maximum movement of the node. With this one can
	 *                         set the maximum position offset the node can reach
	 *                         with the parallax effect
	 * 
	 */
	public void addLayer(Node layer, double parallaxStrength, Movement maxMovement)
	{
		_addLayer(layer, parallaxStrength, maxMovement);
	}

	/**
	 * Adds a new layer to the parallax pane. Layers are stacked on top of each
	 * other, meaning the first layer will be the farthest in the back. <br>
	 * Note: The default parallax strength will be used!
	 * 
	 * @param layer          the layer (can be any JavaFX node) to add the the
	 *                       parallax pane
	 * @param maxMovementthe maximum movement of the node. With this one can set the
	 *                       maximum position offset the node can reach with the
	 *                       parallax effect
	 */

	public void addLayer(Node layer, Movement maxMovement)
	{
		_addLayer(layer, defaultParallaxStrength, maxMovement);
	}

	/**
	 * /** Adds a new layer to the parallax pane. Layers are stacked on top of each
	 * other, meaning the first layer will be the farthest in the back. <br>
	 * Note: The default movement will be used!
	 * 
	 * @param layer            the layer (can be any JavaFX node) to add the the
	 *                         parallax pane
	 * @param parallaxStrength the strength of the parallax effect
	 */
	public void addLayer(Node layer, double parallaxStrength)
	{
		_addLayer(layer, parallaxStrength, defaultMovement);

	}

	/**
	 * Adds a new layer to the parallax pane. Layers are stacked on top of each
	 * other, meaning the first layer will be the farthest in the back. <br>
	 * Note: The default parallax strength and movement will be used!
	 * 
	 * @param layer the layer (can be any JavaFX node) to add the the parallax pane
	 */
	public void addLayer(Node layer)
	{
		_addLayer(layer, defaultParallaxStrength, defaultMovement);
	}

	private void _addLayer(Node layer, double parallaxStrength, Movement maxMovement)
	{
		layersParallaxMap.put(layer, new ParallaxEffect(parallaxStrength, maxMovement));
		getChildren().add(layer);
		defaultPositionMap.put(layer, new DefaultPosition(layer.getTranslateX(), layer.getTranslateY()));

	}

	/**
	 * Returns the scroll multiplier used for faster (or slower) parallax scrolling.
	 * 
	 * @return parallax scrolling multiplier
	 */
	public double getParallaxScrollMultiplier()
	{
		return scrollMultiplier;
	}

	/**
	 * Sets the scroll multiplier used for the parallax scrolling.
	 * 
	 * @param scrollMultiplier the scrolling multiplier
	 */
	public void setParallaxScrollMultiplier(double scrollMultiplier)
	{
		this.scrollMultiplier = scrollMultiplier;
	}

	/**
	 * Removes a layer completely from the parallax pane.
	 * 
	 * @param layer the layer (Node) to remove
	 */
	public void removeLayer(Node layer)
	{
		layersParallaxMap.remove(layer);
		defaultPositionMap.remove(layer);
		getChildren().remove(layer);
	}

	/**
	 * Changes the parallax strength used by the given Node to given value.
	 * 
	 * @param layer            the layer to change the value from
	 * @param parallaxStrenght the parallax strength to change to
	 */
	public void changeParallaxStrength(Node layer, double parallaxStrenght)
	{
		ParallaxEffect parEff = layersParallaxMap.get(layer);
		if (parEff == null)
			return;
		parEff.setStrength(parallaxStrenght);
		layersParallaxMap.put(layer, parEff);
	}

	/**
	 * Changes the movement of the given Node to the given value.
	 * 
	 * @param layer    the layer to change the value from
	 * @param movement the movement to change to
	 */
	public void changeMaxMovement(Node layer, Movement movement)
	{

		ParallaxEffect parEff = layersParallaxMap.get(layer);
		if (parEff == null)
			return;
		parEff.setMaxMovement(movement);
		;
		layersParallaxMap.put(layer, parEff);
	}

	/**
	 * Returns the default parallax strength value.
	 * 
	 * @return default parallax strength
	 */
	public double getDefaultParallaxStrength()
	{
		return defaultParallaxStrength;
	}

	/**
	 * Sets the default parallax strength value.
	 * 
	 * @param defaultParallaxStrength the default parallax strength
	 */
	public void setDefaultParallaxStrength(double defaultParallaxStrength)
	{
		this.defaultParallaxStrength = defaultParallaxStrength;
	}

	/**
	 * Animates all layers to their original position.
	 * 
	 * @param millis the animation time
	 */
	public void animateLayersToOriginalPosition(long millis)
	{
		for (Node n : layersParallaxMap.keySet())
		{
			returnLayerToOriginalPosition(n, millis);
		}
	}

	/**
	 * Removes the parallax mouse scrolling effect.
	 */
	public void removeMouseScrollParallaxEffect()
	{
		setOnScroll(event ->
			{
			});
	}

	/**
	 * Adds the parallax mouse scrolling effect.
	 */
	public void addMouseScrollParallaxEffect()
	{
		setOnScroll(event ->
			{
				_layersList = new ArrayList<Node>(layersParallaxMap.keySet());
				double parallaxStrength = 0;
				if (event.getDeltaY() < 0) // back
				{
					for (Node pane : _layersList)
					{
						parallaxStrength = layersParallaxMap.getOrDefault(pane, defaultParallaxEffect).getStrength()
								* scrollMultiplier;
						if (canMove(pane, MoveDirection.DOWN,
								layersParallaxMap.getOrDefault(pane, defaultParallaxEffect)))
							pane.setTranslateY(pane.getTranslateY() - parallaxStrength);

					}
				} else // front
				{
					for (Node pane : _layersList)
					{
						parallaxStrength = layersParallaxMap.getOrDefault(pane, defaultParallaxEffect).getStrength()
								* scrollMultiplier;
						if (canMove(pane, MoveDirection.UP,
								layersParallaxMap.getOrDefault(pane, defaultParallaxEffect)))

							pane.setTranslateY(pane.getTranslateY() + parallaxStrength);

					}

				}

			});
	}

	/**
	 * Removes the mouse exited animation.
	 */
	public void removeMouseExitedAnimation()
	{
		setOnMouseExited(event ->
			{
			});
	}

	/**
	 * Adds the mouse exited animation which animates all layers to their original
	 * position within 200ms
	 */
	public void addMouseExitedAnimation()
	{
		setOnMouseExited(event ->
			{
				_layersList = new ArrayList<Node>(layersParallaxMap.keySet());

				for (Node n : _layersList)
				{
					returnLayerToOriginalPosition(n, 200);
				}
			});
	}

	/**
	 * Remove mouse moved parallax effect.
	 */
	public void removeMouseMovedParallaxEffect()
	{
		setOnMouseMoved(event ->
			{
			});
	}

	/**
	 * Adds the mouse moved parallax effect.
	 */
	public void addMouseMovedParallaxEffect()
	{
		setOnMouseMoved(event ->
			{
				makeParallaxEffect(event);
			});
	}

	/**
	 * Removes the mouse dragged parallax effect.
	 */
	public void removeMouseDraggedParallaxEffect()
	{
		setOnMouseDragged(event ->
			{
			});
	}

	/**
	 * Adds the mouse dragged parallax effect.
	 */
	public void addMouseDraggedParallaxEffect()
	{
		setOnMouseDragged(event ->
			{
				makeParallaxEffect(event);
			});
	}

	private void makeParallaxEffect(MouseEvent event)
	{
		_layersList = new ArrayList<Node>(layersParallaxMap.keySet());

		mousePosX = event.getSceneX();
		mousePosY = event.getSceneY();

		if (cnt >= 10)
		{

			delta.x = (mousePosX - mouseOldX);
			delta.y = (mousePosY - mouseOldY);
			mouseOldX = mousePosX;
			mouseOldY = mousePosY;
			cnt = 0;
		}

		cnt++;

		mouseOldX = event.getSceneX();
		mouseOldY = event.getSceneY();

		MoveDirection dir = getMouseMoveDirection(delta.x, delta.y);
		for (Node pane : _layersList)
		{
			ParallaxEffect parallaxEffect = layersParallaxMap.getOrDefault(pane, defaultParallaxEffect);
			double parallaxStrength = parallaxEffect.getStrength();

			switch (dir)
			{
			case UP:
				if (canMove(pane, dir, parallaxEffect))
					pane.setTranslateY(pane.getTranslateY() + parallaxStrength);

				break;
			case DOWN:
				if (canMove(pane, dir, parallaxEffect))
					pane.setTranslateY(pane.getTranslateY() - parallaxStrength);

				break;
			case LEFT:
				if (canMove(pane, dir, parallaxEffect))
					pane.setTranslateX(pane.getTranslateX() + parallaxStrength);

				break;
			case RIGHT:
				if (canMove(pane, dir, parallaxEffect))
					pane.setTranslateX(pane.getTranslateX() - parallaxStrength);

				break;
			case UP_LEFT:
				if (canMove(pane, dir, parallaxEffect))
				{
					pane.setTranslateY(pane.getTranslateY() + parallaxStrength);
					pane.setTranslateX(pane.getTranslateX() + parallaxStrength);
				}
				break;
			case UP_RIGHT:
				if (canMove(pane, dir, parallaxEffect))
				{
					pane.setTranslateY(pane.getTranslateY() + parallaxStrength);
					pane.setTranslateX(pane.getTranslateX() - parallaxStrength);
				}
				break;
			case DOWN_LEFT:
				if (canMove(pane, dir, parallaxEffect))
				{
					pane.setTranslateY(pane.getTranslateY() - parallaxStrength);
					pane.setTranslateX(pane.getTranslateX() + parallaxStrength);
				}
				break;
			case DOWN_RIGHT:
				if (canMove(pane, dir, parallaxEffect))
				{
					pane.setTranslateY(pane.getTranslateY() - parallaxStrength);
					pane.setTranslateX(pane.getTranslateX() - parallaxStrength);
				}
				break;

			default:
				break;
			}

		}

	}

	private boolean canMove(Node n, MoveDirection dir, ParallaxEffect parEff)
	{

		DefaultPosition pos = defaultPositionMap.get(n);

		if (pos == null || parEff == null)
			return false;
		Movement maxMove = parEff.getMaxMovement();
		double strength = parEff.getStrength();
		switch (dir)
		{
		case UP:

			return (pos.y + maxMove.getUp() > n.getTranslateY() + strength) ? true : false;
		case DOWN:

			return (pos.y - maxMove.getDown() < n.getTranslateY() - strength) ? true : false;

		case LEFT:
			return (pos.x + maxMove.getLeft() > n.getTranslateX() + strength) ? true : false;

		case RIGHT:
			return (pos.x - maxMove.getRight() < n.getTranslateX() - strength) ? true : false;

		case UP_LEFT:
			return (pos.y + maxMove.getUp() > n.getTranslateY() + strength)
					&& (pos.x + maxMove.getLeft() > n.getTranslateX() + strength) ? true : false;

		case UP_RIGHT:
			return (pos.y + maxMove.getUp() > n.getTranslateY() + strength)
					&& (pos.x - maxMove.getRight() < n.getTranslateX() - strength) ? true : false;

		case DOWN_LEFT:
			return (pos.y - maxMove.getDown() < n.getTranslateY() - strength)
					&& (pos.x + maxMove.getLeft() > n.getTranslateX() + strength) ? true : false;

		case DOWN_RIGHT:
			return (pos.y - maxMove.getDown() < n.getTranslateY() - strength)
					&& (pos.x - maxMove.getRight() < n.getTranslateX() - strength) ? true : false;

		default:
			break;
		}
		return false;
	}

	private MoveDirection getMouseMoveDirection(double mouseDeltaX, double mouseDeltaY)
	{

		if (mouseDeltaY > 0 && mouseDeltaX < 0)
		{
			// System.out.println("DOWN LEFT");
			return MoveDirection.DOWN_LEFT;

		} else if (mouseDeltaY > 0 && mouseDeltaX > 0)
		{
			// System.out.println("DOWN RIGHT");
			return MoveDirection.DOWN_RIGHT;

		} else if (mouseDeltaY < 0 && mouseDeltaX < 0)
		{
			// System.out.println("UP LEFT");
			return MoveDirection.UP_LEFT;

		} else if (mouseDeltaY < 0 && mouseDeltaX > 0)
		{
			// System.out.println("UP RIGHT");
			return MoveDirection.UP_RIGHT;

		} else if (mouseDeltaX < 0)
		{
			// System.out.println("LEFT");
			return MoveDirection.LEFT;

		} else if (mouseDeltaY < 0)
		{
			// System.out.println("UP");
			return MoveDirection.UP;

		} else if (mouseDeltaY > 0)
		{
			// System.out.println("DOWN");

			return MoveDirection.DOWN;

		} else if (mouseDeltaX > 0)
		{
			// System.out.println("RIGHT");
			return MoveDirection.RIGHT;
		}

		return MoveDirection.NO_DIR;

	}

	private void returnLayerToOriginalPosition(Node n, long millis)
	{
		DefaultPosition pos = defaultPositionMap.get(n);
		if (pos != null)
		{
			final Timeline timeline = new Timeline();
			timeline.setCycleCount(1);
			timeline.setAutoReverse(false);
			final KeyValue kvx = new KeyValue(n.translateXProperty(), pos.x);
			final KeyFrame kfx = new KeyFrame(Duration.millis(millis), kvx);
			final KeyValue kvy = new KeyValue(n.translateYProperty(), pos.y);
			final KeyFrame kfy = new KeyFrame(Duration.millis(millis), kvy);

			timeline.getKeyFrames().add(kfx);
			timeline.getKeyFrames().add(kfy);
			timeline.play();
		}
	}

	protected class ParallaxEffect
	{
		private double strength;
		private Movement maxMovement;

		public ParallaxEffect(double strength, Movement maxMovement)
		{
			super();
			this.strength = strength;
			this.maxMovement = maxMovement;
		}

		public double getStrength()
		{
			return strength;
		}

		public void setStrength(double strength)
		{
			this.strength = strength;
		}

		public Movement getMaxMovement()
		{
			return maxMovement;
		}

		public void setMaxMovement(Movement maxMovement)
		{
			this.maxMovement = maxMovement;
		}

	}

	protected class DefaultPosition
	{
		private double x;
		private double y;

		public DefaultPosition(double x, double y)
		{
			this.x = x;
			this.y = y;
		}

		public double getX()
		{
			return x;
		}

		public void setX(double x)
		{
			this.x = x;
		}

		public double getY()
		{
			return y;
		}

		public void setY(double y)
		{
			this.y = y;
		}

	}

	protected class Delta
	{
		double x;
		double y;
	}

}
