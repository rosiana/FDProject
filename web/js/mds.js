Array.prototype.enorm = function () {
    return Math.sqrt(this.reduce(function(prev,cur) { return prev + cur*cur; },0));
}

dist = function(a,b){
    return (a.add(b.mult(-1))).enorm();
}

Array.prototype.add = function (b) {
    var s = Array(this.length);
    for (var ind = 0; ind < this.length; ind++)
    {
        if (typeof(b)=="number")
        {
            s[ind] = this[ind]+ b;
        }
        else
        {
            s[ind] = this[ind]+ b[ind];
        }
    }
    return s;
};

Array.prototype.mult = function (b) {
    var s = Array(this.length);
    for (var ind = 0; ind < this.length; ind++)
    {
        if (typeof(b)=="number")
        {
            s[ind] = this[ind]* b;
        }
        else
        {
            s[ind] = this[ind]* b[ind];
        }
    }
    return s;
};


Array.prototype.norm = function () {
    var s = 0;
    for (var ind = 0; ind < this.length; ind++)
    {
        s[ind] = this[ind] + b[ind];
    }
    return s;
};

Array.prototype.max = function () {
    return Math.max.apply(Math, this);
};

Array.prototype.min = function () {
    return Math.min.apply(Math, this);
};
Array.prototype.range = function () {
    return [this.min(), this.max()];
};
function project(a,b,r)
{
    var d = a.add(b.mult(-1));
    var rat=r/d.enorm();
    return b.add(d.mult(rat));
}


//Used to expand slightly the plotting window
expand = function(r)
{
    var d = r[1] - r[0],alpha=.1;
    return r.add([-alpha*d, alpha*d]);
}

d3scatterplot = function(svg,X,D,cities) {
    var nPix= 420,n=X.length,mar = [40,60,40,40];

    var xv = X.map(function(e) { return e.x;}),xRange=expand(xv.range());
    var yv = X.map(function(e) { return e.y;}),yRange=expand(yv.range());
    svg.attr("width", nPix+mar[0]+mar[2])
        .attr("height", nPix+mar[1]+mar[3]);


    var sg = svg.append("g")
        .attr("transform", "translate("
            + mar[0] + ","
            + mar[1] + ")");




    var xScale = d3.scale.linear()
        .range([0, nPix])
        .domain(xRange);

    var yScale = d3.scale.linear()
        .range([nPix, 0])
        .domain(yRange);


    var labels = sg.selectAll(".labels")
        .data(X).enter()
        .append("text")
        .attr("class", "label")
        .attr("x",function(d) {return xScale(d.x);})
        .attr("y",function(d) {return yScale(d.y);})
        .text(function(d,i) {return cities[i];})
        .attr("font-size",10)
        .attr("id",function(d,i) {return "label" + i});



    var dots = sg.selectAll(".datapoint")
        .data(X).enter()
        .append("circle")
        .attr("class", "datapoint")
        .attr("cx",function(d) {return xScale(d.x);})
        .attr("cy",function(d) {return yScale(d.y);})
        .attr("id",function(d,i) {return "point" + i})
        .attr("r",2);

    var xAxis = d3.svg.axis().scale(xScale).orient("bottom").ticks(4);
    svg.append("g").call(xAxis)
        .attr("class", "axis")  //Assign "axis" class
        .attr("transform","translate(" + mar[0] + "," + (nPix+mar[1])  + ")");

    var yAxis = d3.svg.axis().scale(yScale).orient("left").ticks(4);
    svg.append("g").call(yAxis)
        .attr("class", "axis")  //Assign "axis" class
        .attr("transform","translate(" + mar[0] + "," + (mar[3])  + ")");

    svg.on("mouseover",function(d,i)
    {
        labels.data(Xn)
            .transition()
            .attr("x",function(d) {return xScale(d.x);})
            .attr("y",function(d) {return yScale(d.y);});

    });

    reset = function(el)
    {
        labels.data(X)
            .attr("x",function(d) {return xScale(d.x);})
            .attr("y",function(d) {return yScale(d.y);});
    }

    svg.on("mouseout",reset); //Reset also if the mouse leaves the frame, the capture of "mouseout" events being rather unreliable

}


d3.json("tes.json", function(data) {
    var  svg = d3.select("#d3plot").append("svg")
        .attr("width","100%")
        .attr("height","100%");

    d3scatterplot(svg,data.X,data.D,data.cities);
});