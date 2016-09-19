/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();
    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter)
        regexp = new RegExp(seriesFilter, 'i');

    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            var newRow = tBody.insertRow(-1);
            for(var col=0; col < item.data.length; col++){
                var cell = newRow.insertCell(-1);
                cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 100.0, "KoPercent": 0.0};
    var dataset = [
        {
            "label" : "KO",
            "data" : data.KoPercent,
               "color" : "red"
        },
        {
            "label" : "OK",
            "data" : data.OkPercent,
            "color" : "blue"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round(series.percent)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.7884615384615384, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)  ", "F (Frustration threshold)", "Label"], "items": [{"data": [1.0, 500, 1500, "Get details of space"], "isController": false}, {"data": [1.0, 500, 1500, "Add space to collection"], "isController": false}, {"data": [0.5, 500, 1500, "Spaces Home page"], "isController": false}, {"data": [0.5, 500, 1500, "Go to Tracker Page"], "isController": false}, {"data": [0.5, 500, 1500, "Link file from Dropbox"], "isController": false}, {"data": [1.0, 500, 1500, "Download file from space"], "isController": false}, {"data": [0.5, 500, 1500, "Download all files from spaces"], "isController": false}, {"data": [0.5, 500, 1500, "login"], "isController": false}, {"data": [0.5, 500, 1500, "Upload file 1"], "isController": false}, {"data": [1.0, 500, 1500, "User spaces"], "isController": false}, {"data": [0.5, 500, 1500, "Upload file 2"], "isController": false}, {"data": [1.0, 500, 1500, "Delete collection"], "isController": false}, {"data": [0.5, 500, 1500, "Upload file 3"], "isController": false}, {"data": [1.0, 500, 1500, "Share a space"], "isController": false}, {"data": [0.5, 500, 1500, "Upload file 4"], "isController": false}, {"data": [0.5, 500, 1500, "Upload file 5"], "isController": false}, {"data": [1.0, 500, 1500, "logout"], "isController": false}, {"data": [1.0, 500, 1500, "user details"], "isController": false}, {"data": [1.0, 500, 1500, "Send the file"], "isController": false}, {"data": [1.0, 500, 1500, "Create new space"], "isController": false}, {"data": [1.0, 500, 1500, "Go to Send page"], "isController": false}, {"data": [1.0, 500, 1500, "Delete space"], "isController": false}, {"data": [1.0, 500, 1500, "Send space share email"], "isController": false}, {"data": [1.0, 500, 1500, "space name"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 26, 0, 0.0, 1103.0, 1243.4499999999998, 1304.0, 2.4588613580480425, 24.452574563788538, 51, 1304], "isController": false}, "titles": ["Label", "#Samples", "KO", "Error %", "90th pct", "95th pct", "99th pct", "Throughput", "KB/sec", "Min", "Max"], "items": [{"data": ["Get details of space", 2, 0, 0.0, 93.0, 93.0, 93.0, 0.3829216925138809, 0.34216147329121194, 88, 93], "isController": false}, {"data": ["Add space to collection", 1, 0, 0.0, 156.0, 156.0, 156.0, 6.41025641025641, 6.447816506410256, 156, 156], "isController": false}, {"data": ["Spaces Home page", 1, 0, 0.0, 1091.0, 1091.0, 1091.0, 0.9165902841429882, 2.4722874656278644, 1091, 1091], "isController": false}, {"data": ["Go to Tracker Page", 1, 0, 0.0, 1304.0, 1304.0, 1304.0, 0.7668711656441718, 16.86742115605828, 1304, 1304], "isController": false}, {"data": ["Link file from Dropbox", 1, 0, 0.0, 522.0, 522.0, 522.0, 1.9157088122605364, 1.8764218151340994, 522, 522], "isController": false}, {"data": ["Download file from space", 1, 0, 0.0, 190.0, 190.0, 190.0, 5.263157894736842, 142.5010279605263, 190, 190], "isController": false}, {"data": ["Download all files from spaces", 1, 0, 0.0, 1131.0, 1131.0, 1131.0, 0.8841732979664013, 165.52518512378427, 1131, 1131], "isController": false}, {"data": ["login", 1, 0, 0.0, 1067.0, 1067.0, 1067.0, 0.9372071227741331, 1.8744142455482662, 1067, 1067], "isController": false}, {"data": ["Upload file 1", 2, 0, 0.0, 577.0, 577.0, 577.0, 0.6071645415907712, 0.590858853217972, 566, 577], "isController": false}, {"data": ["User spaces", 1, 0, 0.0, 133.0, 133.0, 133.0, 7.518796992481203, 10.404429041353383, 133, 133], "isController": false}, {"data": ["Upload file 2", 1, 0, 0.0, 677.0, 677.0, 677.0, 1.4771048744460857, 1.4367152880354503, 677, 677], "isController": false}, {"data": ["Delete collection", 1, 0, 0.0, 157.0, 157.0, 157.0, 6.369426751592357, 4.596685907643312, 157, 157], "isController": false}, {"data": ["Upload file 3", 1, 0, 0.0, 562.0, 562.0, 562.0, 1.779359430604982, 1.730705071174377, 562, 562], "isController": false}, {"data": ["Share a space", 1, 0, 0.0, 113.0, 113.0, 113.0, 8.849557522123893, 5.781595685840708, 113, 113], "isController": false}, {"data": ["Upload file 4", 1, 0, 0.0, 523.0, 523.0, 523.0, 1.9120458891013383, 1.8597633843212236, 523, 523], "isController": false}, {"data": ["Upload file 5", 1, 0, 0.0, 522.0, 522.0, 522.0, 1.9157088122605364, 1.8633261494252873, 522, 522], "isController": false}, {"data": ["logout", 1, 0, 0.0, 75.0, 75.0, 75.0, 13.333333333333334, 7.34375, 75, 75], "isController": false}, {"data": ["user details", 1, 0, 0.0, 51.0, 51.0, 51.0, 19.607843137254903, 16.161151960784316, 51, 51], "isController": false}, {"data": ["Send the file", 1, 0, 0.0, 163.0, 163.0, 163.0, 6.134969325153374, 2.857793328220859, 163, 163], "isController": false}, {"data": ["Create new space", 1, 0, 0.0, 364.0, 364.0, 364.0, 2.7472527472527473, 2.720424107142857, 364, 364], "isController": false}, {"data": ["Go to Send page", 1, 0, 0.0, 179.0, 179.0, 179.0, 5.58659217877095, 5.521124301675978, 179, 179], "isController": false}, {"data": ["Delete space", 1, 0, 0.0, 66.0, 66.0, 66.0, 15.151515151515152, 7.0578835227272725, 66, 66], "isController": false}, {"data": ["Send space share email", 1, 0, 0.0, 61.0, 61.0, 61.0, 16.393442622950822, 7.636398565573771, 61, 61], "isController": false}, {"data": ["space name", 1, 0, 0.0, 70.0, 70.0, 70.0, 14.285714285714285, 6.654575892857142, 70, 70], "isController": false}]}, function(index, item){
        switch(index){
            case 3:
                item = item.toFixed(2) + '%';
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": []}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);
});
