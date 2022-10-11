package com.newgbaxl.blastmaze.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

/** @author Krustnic */
public class HorizontalSlidingPane extends HorizontalPane {

    private float sectionWidth, sectionHeight;
    // Section container
    private Group sections;
    // Offset
    private float amountX = 0;

    // Section movement direction
    private int transmission   = 0;
    private float stopSection  = 0;
    private float speed        = 1500;
    private int currentSection = 1;
    // Speed (pixel/sec) to move to next section
    private float flingSpeed   = 1000;

    private float overscrollDistance = 500;

    private Rectangle cullingArea = new Rectangle();
    private Actor touchFocusedChild;
    private ActorGestureListener actorGestureListener;

    public HorizontalSlidingPane() {

        sections = new Group();
        this.addActor( sections );

        sectionWidth  = Gdx.app.getGraphics().getWidth();
        sectionHeight = Gdx.app.getGraphics().getHeight();

        actorGestureListener = new ActorGestureListener() {

            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {

            }

            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {

                if ( amountX < -overscrollDistance ) return;
                if ( amountX > (sections.getChildren().size - 1) * sectionWidth + overscrollDistance) return;

                amountX -= deltaX;


                cancelTouchFocusedChild();
            }

            @Override
            public void fling (InputEvent event, float velocityX, float velocityY, int button) {

                if ( Math.abs(velocityX) > flingSpeed ) {
                    if ( velocityX > 0 ) setStopSection(currentSection - 2);
                    else setStopSection(currentSection);
                }

                cancelTouchFocusedChild();
            }

            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //if ( event.getTarget().getClass() == LevelIcon.class ) {
                    touchFocusedChild = event.getTarget();
                //}
            }
        };

        this.addListener(actorGestureListener);
    }

    public void addWidget(Actor widget) {
        widget.setX( this.sections.getChildren().size * sectionWidth );
        widget.setY( 0 );
        widget.setWidth( sectionWidth );
        widget.setHeight( sectionHeight );

        sections.addActor( widget );
    }

    // Calculate current section based on offset of sections container
    public int calculateCurrentSection() {
        // Current section = (Current offset / section length) + 1, b/c start from index 1
        int section = Math.round( amountX / sectionWidth ) + 1;
        // Checks the adequacy of the obtained value, within the interval [1, number of sections]
        if ( section > sections.getChildren().size ) return sections.getChildren().size;
        if ( section < 1 ) return 1;
        return section;
    }

    public int getSectionsCount() {
        return sections.getChildren().size;
    }

    public void setStopSection(int stoplineSection) {
        if ( stoplineSection < 0 ) stoplineSection = 0;
        if ( stoplineSection > this.getSectionsCount() - 1 ) stoplineSection = this.getSectionsCount() - 1;

        stopSection = stoplineSection * sectionWidth;

        // Determine movement direction
        // transmission ==  1 - right
        // transmission == -1 - left
        if ( amountX < stopSection) {
            transmission = 1;
        }
        else {
            transmission = -1;
        }
    }

    private void move(float delta) {
        // Determine offset direction
        if ( amountX < stopSection) {
            // Move right
            // You had to move left to get here
            // Time to stop
            if ( transmission == -1 ) {
                amountX = stopSection;
                // Fix the number of current selection
                currentSection = calculateCurrentSection();
                return;
            }
            // Shift
            amountX += speed * delta;
        }
        else if( amountX > stopSection) {
            if ( transmission == 1 ) {
                amountX = stopSection;
                currentSection = calculateCurrentSection();
                return;
            }
            amountX -= speed * delta;
        }
    }

    @Override
    public void act (float delta) {
        // Move the container sections
        sections.setX( -amountX );

        cullingArea.set( -sections.getX() + 50, sections.getY(), sectionWidth - 100, sectionHeight );
        sections.setCullingArea(cullingArea);

        // If you move your finger across the screen
        if ( actorGestureListener.getGestureDetector().isPanning() ) {
            // Set the border to animate the movement
            // Border = previous section number
            setStopSection(calculateCurrentSection() - 1);
        }
        else {
            // Animate the movement to a given point ff the finger is far from the screen
            move( delta );
        }
    }

    void cancelTouchFocusedChild () {
        if (touchFocusedChild == null) return;
        //try {
        //    this.getStage().cancelTouchFocus(this.actorGestureListener, this);
        //} catch (Exception e) {
        //}
        touchFocusedChild = null;
    }

    public void setFlingSpeed( float _flingSpeed ) {
        flingSpeed = _flingSpeed;
    }

    public void setSpeed( float _speed ) {
        speed = _speed;
    }

    public void setOverscrollDistance( float _overscrollDistance ) {
        overscrollDistance = _overscrollDistance;
    }

}
