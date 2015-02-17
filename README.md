#GQuery Elastic Plugin

##Description of the plugin
Elastic plugin takes the children of an element and rearranges them in a grid layout (like google+ or pinterest)

##Examples
Check these 2 examples:

http://arcbees.github.io/gwtquery-elastic-plugin/ElasticWidgetSample/WidgetSample.html

http://arcbees.github.io/gwtquery-elastic-plugin/ElasticBasicSample/ElasticLayoutSample.html

##How it works ?
To call the plugin:

```java
    import static com.arcbees.gquery.elastic.client.Elastic.Elastic;

    ElasticOption option = new ElasticOption()
           .withColumWidth(400)
           .withMinimalNumberOfColumn(2);

            $("#container").as(Elastic).elastic(option);
```

or you can use an `ElasticHtmlPanel` and all direct children of the panel (html elements or widgets) will be rearranged in column.



##How to configure it

The different options are:
* _minimumColumnWidth_: Specify the minimum width of the column. The plugin uses this value to maximise the 
number of column to display. (default: 250px)
* _minimalNumberOfColumn_: Specify the minimum number of column. (default: 1)
* _maximumNumberOfColumn_: Specify the maximum number of column. (default: infinity)
* _maximumNumberOfColumn_: Specify the maximum number of column. (default: infinity)
* _InnerColumnMargin_: Specify the space between each column (default: 10px)
* _InnerRowMargin_: Specify the space between each row (default: 10px)
*_autoResize_: Specify if we have to recalculate the layout after a window resizing. (default: true)

To specify this option, you can use an `ElasticOption` object and pass it to the plugin or uses the specific method directly on the ElasticHtmlPanel

On each child we can specify the following attributes:
*_data-elastic-span_: specify the number of column to span for this child.
*_data-elastic-column_: Force the child to be displayed in a specific column. "first" and "last" values are also accepted and the child will be displayed respectively in the first of in the last column no matter the number of column

## How to install

There is no official release yet, but you can already use a snapshot version
###With maven :
```xml
    <dependency>
        <groupId>com.arcbees.gquery</groupId>
        <artifactId>elastic</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
```

##Thanks to
[![Arcbees.com](http://i.imgur.com/HDf1qfq.png)](http://arcbees.com)

[![Atlassian](http://i.imgur.com/BKkj8Rg.png)](https://www.atlassian.com/)

[![IntelliJ](https://lh6.googleusercontent.com/--QIIJfKrjSk/UJJ6X-UohII/AAAAAAAAAVM/cOW7EjnH778/s800/banner_IDEA.png)](http://www.jetbrains.com/idea/index.html)
