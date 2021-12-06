# SlideActionView

A HMOS library which provides slide-to-left/right interaction.

## Source
Inspired by [fennifith/SlideActionView](https://github.com/fennifith/SlideActionView) - version 0.0.2

## Feature
SlideActionView is a simple widget that provides a nice slide-to-left/right interaction.

<img src="https://github.com/priyankabb153/SlideActionView/blob/main/screenshots/slideactionview.gif" width="256">

## Dependency
1 . For using slideactionview module in sample app, include the source code and add the below dependencies in entry/build.gradle to generate hap/support.har.
```groovy
	dependencies {
		implementation project(':slideactionview')
                implementation fileTree(dir: 'libs', include: ['*.har'])
                testImplementation 'junit:junit:4.13'
	}
```
2 . For using slideactionview in separate application using har file, add the har file in the entry/libs folder and add the dependencies in entry/build.gradle file.
```groovy
	dependencies {
		implementation fileTree(dir: 'libs', include: ['*.har'])
		testImplementation 'junit:junit:4.13'
	}
```

## Usage

Adding the SlideActionView somewhere in your layout is fairly simple. Here is an example:

```xml
<me.jfenn.slideactionview.SlideActionView
    ohos:id="$+id:SlideAction"
    ohos:background_element="#30000000"
    ohos:height="180vp"
    ohos:layout_alignment="bottom"
    ohos:width="match_parent"/>
```

You will then want to specify icons for the left/right "slides". This can be done using the ``setLeftIcon`` and ``setRightIcon`` methods of the view. They accept both a ``Element`` and ``PixelMap``, but it is more efficient to pass a ``PixelMap`` if possible.

```java
SlideActionView view = (SlideActionView) findComponentById(ResourceTable.Id_SlideAction);
Optional<PixelMapElement> element = getElementByResId(ResourceTable.Media_cancel_2);
PixelMap pixelMap = getPixelMapFromDrawable(element.get()).get();
view.setLeftIcon(pixelMap);
Optional<PixelMapElement> element1 = getElementByResId(ResourceTable.Media_unlock_2);
PixelMap pixelMap1 = getPixelMapFromDrawable(element1.get()).get();
view.setRightIcon(pixelMap1);
```
In order to listen for the swipe actions, you must implement the ``SlideActionListener`` interface.
```java
view.setListener(new SlideActionView.SlideActionListener() {
  @Override
  public void onSlideLeft() {
    // slid left
  }
  
  @Override
  public void onSlideRight() {
    // slid right
  }
});
 ``` 

#### Theming
There are several methods that you can call to specify different colors. I will not go into great detail of what they do, but it should be fairly obvious. setTouchHandleColor changes the color of the touch handle. setOutlineColor affects the outlines. setIconColor changes the filter applied to both icons.

#### Future Work
Since svg format images cannot be converted to pixelmap using drawabletobitmap function of imageutils class, images in png format are being used to set the right and left icons.
```java
Optional<PixelMapElement> element1 = getElementByResId(ResourceTable.Media_unlock_2);
PixelMap pixelMap1 = getPixelMapFromDrawable(element1.get()).get();
view.setRightIcon(pixelMap1);
```

