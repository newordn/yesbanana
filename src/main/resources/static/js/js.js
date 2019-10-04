/**
 * Created by derteuffel on 29/11/2018.
 */
//get the input and UL list
var input = document.getElementById('files');
var list = document.getElementById('fileList');
//empty list for now...
while (list.hasChildNodes()) {
    list.removeChild(ul.firstChild);
}

//for every file...
for (var x = 0; x < input.files.length; x++) {
    //add to list
    var li = document.createElement('li');
    li.innerHTML = 'File ' + (x + 1) + ':  ' + input.files[x].name;
    list.append(li);
}
