(function(mds) {
    "use strict";
    /// given a matrix of distances between some points, returns the
    /// point coordinates that best approximate the distances using
    /// classic multidimensional scaling
    mds.classic = function(distances, dimensions) {
        dimensions = dimensions || 2;

        // square distances
        var M = numeric.mul(-0.5, numeric.pow(distances, 2));

        // double centre the rows/columns
        function mean(A) { return numeric.div(numeric.add.apply(null, A), A.length); }
        var rowMeans = mean(M),
            colMeans = mean(numeric.transpose(M)),
            totalMean = mean(rowMeans);

        for (var i = 0; i < M.length; ++i) {
            for (var j =0; j < M[0].length; ++j) {
                M[i][j] += totalMean - rowMeans[i] - colMeans[j];
            }
        }

        // take the SVD of the double centred matrix, and return the
        // points from it
        var ret = numeric.svd(M),
            eigenValues = numeric.sqrt(ret.S);
        return ret.U.map(function(row) {
            return numeric.mul(row, eigenValues).splice(0, dimensions);
        });
    };

    /// draws a scatter plot of points, useful for displaying the output
    /// from mds.classic etc
    mds.drawD3ScatterPlot = function(element, xPos, yPos, labels, params, radius, nama) {
        params = params || {};
        var padding = params.padding || 32,
            w = params.w || Math.min(720, document.documentElement.clientWidth - padding),
            h = params.h || w,
            xDomain = [Math.min.apply(null, xPos),
                       Math.max.apply(null, xPos)],
            yDomain = [Math.max.apply(null, yPos),
                       Math.min.apply(null, yPos)],
            pointRadius = params.pointRadius || 3;

        if (params.reverseX) {
            xDomain.reverse();
        }
        if (params.reverseY) {
            yDomain.reverse();
        }

        var xScale = d3.scale.linear().
                domain(xDomain)
                .range([padding, w - padding]),

            yScale = d3.scale.linear().
                domain(yDomain)
                .range([padding, h-padding]),

            xAxis = d3.svg.axis()
                .scale(xScale)
                .orient("bottom")
                .ticks(params.xTicks || 7),

            yAxis = d3.svg.axis()
                .scale(yScale)
                .orient("left")
                .ticks(params.yTicks || 7);

        var svg = element.append("svg")
                .attr("width", w)
                .attr("height", h);

        svg.append("g")
            .attr("class", "axis")
            .attr("transform", "translate(0," + (h - padding + 2*pointRadius) + ")")
            .call(xAxis);

        svg.append("g")
            .attr("class", "axis")
            .attr("transform", "translate(" + (padding - 2*pointRadius) + ",0)")
            .call(yAxis);

        var nodes = svg.selectAll("circle")
            .data(labels)
            .enter()
            .append("g");

        var list = [];
        for (var j in radius)
            list.push({'sRadius': radius[j], 'sXPos': xPos[j], 'sYPos': yPos[j]});

        list.sort(function(a, b) {
            return ((a.sRadius > b.sRadius) ? -1 : ((a.sRadius == b.sRadius) ? 0 : 1));
        });
        for (var k = 0; k < list.length; k++) {
            radius[k] = list[k].sRadius;
            xPos[k] = list[k].sXPos;
            yPos[k] = list[k].sYPos;
        }

        /*
        nodes.append("circle")
            .attr("r", 5)
            .attr("cx", function(d, i) { return xScale(xPos[i]); })
            .attr("cy", function(d, i) { return yScale(yPos[i]); })
            //new
            .attr("r", function(d, i) { return radius[i]/5; })
            .style("fill", "#65839b")
            //.style("fill-opacity", 0.2)
            .style("stroke", "#3a425c")
            .style("stroke-opacity", 1)
            .style("stroke-width", 1);
            //.on("mouseover", function() {d3.select(this).style("fill-opacity", 1) })
            //.on("mouseout", function() {d3.select(this).style("fill-opacity", 0.2) });

        nodes.append("text")
            .attr("text-anchor", "middle")
            .text(function(d) { return d; })
            .attr("x", function(d, i) { return xScale(xPos[i]); })
            .attr("y", function(d, i) { return yScale(yPos[i]) - 2 *pointRadius; })
            .style("visibility", "hidden");

        $('svg circle').tipsy({
            gravity: 's',
            html: true,
            title: function() {
                var d = this.__data__;
                return d;
            }
        });

         */

        //plotly

        var plotlyX = [], plotlyY = [];

        for (var i = 0; i < xPos.length; i++) {
            plotlyX[i] = xScale(xPos[i]);
            plotlyY[i] = yScale(yPos[i]);
        }

        console.log(labels);

        var trace = {
            x: plotlyX,
            y: plotlyY,
            mode: 'markers',
            marker: {
                color: 'rgb(101, 131, 155)',
                sizemode: 'area',
                size: radius
            },
            text: labels
        };

        var data = [trace];

        var layout = {
            margin: {t: 20}
        };

        Plotly.plot('myDiv', data, layout, {displayModeBar: true, displaylogo: false});
    };
}(window.mds = window.mds || {}));

var matrix;
var labels;
var radius;
var nama;

d3.json("json/t2a1.json", function(data) {
    console.log(data);
    matrix = data.matrix;

    var labelstemp = data.lelang.map(function(d) {
        return d.id;
    });
    labels = labelstemp;

    var radiustemp = data.lelang.map(function(d) {
        return d.jumlahpeserta;
    });
    radius = radiustemp;

    var namatemp = data.lelang.map(function(d) {
        return d.nama;
    });
    nama = namatemp;

    var dotPositions = numeric.transpose(mds.classic(matrix));
    var w = Math.min(720, document.documentElement.clientWidth - 20),
        h = w /2;

    mds.drawD3ScatterPlot(d3.select("#cities"),
        dotPositions[0],
        dotPositions[1],
        labels,
        {
            w :  Math.min(1366, document.documentElement.clientWidth - 20),
            h : w,
            padding : 37,
            reverseX : true,
            reverseY : true
        },
        radius,
        test
        );
});

