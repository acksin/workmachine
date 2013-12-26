(function($){
    var Job = Backbone.Model.extend({
        urlRoot: '/v1/job',

        initialize: function() {

        }
    });

    var JobView = Backbone.View.extend({
        el: $('#content'),

        events: {
            'click button#add': 'addItem'
        },

        initialize: function(){
            _.bindAll(this, 'render', 'addItem'); // every function that uses 'this' as the current object should be in here

            this.counter = 0; // total number of items added thus far
            this.render();
        },
        // `render()` now introduces a button to add a new list item.
        render: function(){
            $(this.el).append("<button id='add'>Submit</button>");
            $(this.el).append("<ul></ul>");
        },

        // `addItem()`: Custom function called via `click` event above.
        addItem: function(){
            this.counter++;
            $('ul', this.el).append("<li>hello world"+this.counter+"</li>");
        }
    });

    var jobView = new JobView();
})(jQuery);
