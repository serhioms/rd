/**
 * Drawing VSF Diagrams
 */

/**
 * Namespace for PASS
 */
var grafra = grafra || {};

grafra.dia = grafra.dia || {};



/**
 * template for an diagram arrow
 */
grafra.dia.arrow = joint.dia.Link.extend({
    defaults : {
        type : 'vsf.Link',
        attrs : {
            '.marker-target' : {
                d : 'M 10 0 L 0 5 L 10 10 z',
                fill : '#34495e',
                stroke : '#2c3e50'
            },
            '.connection' : {
                stroke : '#2c3e50',
                'stroke-width' : 2,
            },

        // disabling arrow heads prevents from showing handles for moving arroes
        // '.marker-arrowheads' : { display: 'none' },
        // '.link-tools' : { display: 'none' },

        },
        smooth : true,

        // The default markup for links.
        markup : [ '<path class="connection" stroke="black"/>',
                '<path class="marker-source" fill="black" stroke="black" />',
                '<path class="marker-target" fill="black" stroke="black" />', '<path class="connection-wrap"/>',
                '<g class="labels" />', '<g class="marker-vertices"/>',
        // '<g class="marker-arrowheads"/>',
        // '<g class="link-tools" />'
        ].join(''),

    }
});

/**
 * Prototype for a start node
 */
grafra.dia.StartNode = joint.shapes.basic.Circle.extend({

    defaults : joint.util.deepSupplement({

        type : 'vsf.StartNode',
        attrs : {
            circle : {
                'fill' : 'skyblue',
                'stroke' : 'black',
                'stroke-width' : 2,
                'rx' : 1
            }
        }

    }, joint.shapes.basic.Circle.prototype.defaults)

});


// Will not be triggered
grafra.dia.StartNodeView = joint.dia.ElementView.extend({
    pointerdown: function(evt) {
        
        console.log('View was here menu');
        joint.dia.ElementView.prototype.pointerdown.apply(this, arguments);
        
        if (evt.which === 3) {
            // Here you can create your custom context menu or do whatever is needed as a reaction on the mouse right button.
            console.log('open context menu');
            alert('context menu');
        }

    }
});




/**
 * Prototype for a end node
 */
grafra.dia.EndNode = joint.shapes.basic.Generic.extend({

    markup : '<g class="rotatable"><g class="scalable"><circle class="outer"/><circle class="inner"/></g></g>',

    defaults : joint.util.deepSupplement({

        type : 'vsf.EndNode',
        size : {
            width : 20,
            height : 20
        },
        attrs : {
            'circle.outer' : {
                transform : 'translate(10, 10)',
                r : 10,
                fill : 'white',
                stroke : '#2c3e50'
            },

            'circle.inner' : {
                transform : 'translate(10, 10)',
                r : 6,
                fill : 'blue'
            }
        }

    }, joint.shapes.basic.Generic.prototype.defaults)

});




grafra.dia.Group = joint.shapes.basic.Generic.extend({

    markup: [
        '<g class="rotatable">',
          '<g class="scalable">',
            '<rect/>',
          '</g>',
          '<path/><text class="uml-state-name"/><text class="uml-state-events"/>',
        '</g>'
    ].join(''),

    defaults: joint.util.deepSupplement({

        type: 'uml.State',

        attrs: {
            rect: { 'width': 20, 'height': 20, 'fill': '#ecf0f1', 'stroke': '#bdc3c7', 'stroke-width': 3, 'rx': 1, 'ry': 1 },
            path: { 'd': 'M 0 20 L 20 20', 'stroke': '#bdc3c7', 'stroke-width': 2 },
            text: { 'font-family': 'Courier New', 'font-size': 14, fill: 'black' },
            '.uml-state-name': { 'ref': 'rect', 'ref-x': .5, 'ref-y': 5, 'text-anchor': 'middle' },
            '.uml-state-events': { 'ref': 'path', 'ref-x': 5, 'ref-y': 5 }
        },

        name: 'State',
        events: []

    }, joint.shapes.basic.Generic.prototype.defaults),

    initialize: function() {

        _.bindAll(this, 'updateEvents', 'updatePath');

        this.on({
            'change:name': function() { this.updateName(); this.trigger('change:attrs'); },
            'change:events': function() { this.updateEvents(); this.trigger('change:attrs'); },
            'change:size': this.updatePath
        });

        this.updateName();
        this.updateEvents();
        this.updatePath();

        joint.shapes.basic.Generic.prototype.initialize.apply(this, arguments);
    },

    updateName: function() {
        this.get('attrs')['.uml-state-name'].text = this.get('name');
    },

    updateEvents: function() {
        this.get('attrs')['.uml-state-events'].text = this.get('events').join('\n');
    },

    updatePath: function() {
        this.get('attrs')['path'].d = 'M 0 20 L ' + this.get('size').width + ' 20';
    }

});






var graph = new joint.dia.Graph;

var paper = new joint.dia.Paper({
    el : $("#paper"),
    width : 800,
    height : 400,
    gridSize : 10,
    perpendicularLinks : false,
    model : graph
});



var paperSmall = new joint.dia.Paper({
    el : $('#map'),
    width : 300,
    height : 200,
    gridSize : 10,
    perpendicularLinks : true,
    model : graph
});

paperSmall.scale(.1);
// disable all pointer events
paperSmall.$el.css('pointer-events', 'none');






var start = new grafra.dia.StartNode({
    position : {
        x : 50,
        y : 150
    }, 
    size : {
        width : 16,
        height : 16
    }
});
graph.addCell(start);



var end = new grafra.dia.EndNode({
    position : {
        x : 700,
        y : 150
    }, 
    size : {
        width : 16,
        height : 16
    }
});
graph.addCell(end);




var l1 = new grafra.dia.arrow({
    source : {
        id : start.id
    },
    target : {
        id : end.id
    },
    vertices : [ {
        x : 150,
        y : 50
    }, {
        x : 600,
        y : 50
    }, ],
});
graph.addCell(l1);

console.log( " create group");


//var g2 = new  joint.shapes.uml.State({
    

var g2 = new  grafra.dia.Group({
    position: { x:250  , y: 200 },
    size: { width: 300, height: 200},
    name: "mygroup",
    events: ["group"]
});
graph.addCell(g2);






var l2 = new grafra.dia.arrow({
    source : {
        id : start.id
    },
    target : {
        id : g2.id
    },
    
});
graph.addCell(l2);



var l3 = new grafra.dia.arrow({
    source : {
        id : g2.id
    },
    target : {
        id : end.id
    },
    
});
graph.addCell(l3);


var s2 = new grafra.dia.StartNode({
    position : {
        x : 300,
        y : 250
    }, 
    size : {
        width : 16,
        height : 16
    }, name : "s2"
});
graph.addCell(s2);

var s3 = new grafra.dia.StartNode({
    position : {
        x : 350,
        y : 300
    }, 
    size : {
        width : 16,
        height : 16
    }, name : "s3"
});


graph.addCell(s3);
s3.on('change:position', function(obj, pos , r) {
    
    console.log(s3.get('position')); // do something here
 });



g2.embed(s3);





console.log( " registering handler  ");

//graph.on('all', function(eventName, cell)
//{
//    console.log(arguments);
//});

paper.on('cell', function(v, evt, x, y)
{
    console.log(arguments);

})

paper.on('blank:pointerdown', function(evt, x, y)
{
    console.log("pointerdown on " + x + "," + y);
})

paper.on('cell:pointerdown', function(v, evt, x, y)
{
    console.log("mousedown on " + x + "," + y);
})

paper.on('cell:pointerup', function(v, evt, x, y)
{
    console.log("mouseup on " + x + "," + y);
})
paper.on('cell:pointermove', function(v, evt, x, y)
{
    console.log("mousemove on " + x + "," + y);
})




