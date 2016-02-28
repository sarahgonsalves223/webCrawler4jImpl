
var array = db.new_inverted_index.distinct("term"); //gets all the distinct terms
var i = 0; 
while(i<array.length){
var term_name = array[i];
var id = db.new_inverted_index.find({term:term_name}).toArray(); //gets the record for every term
var df_count = id[0].docs.length; //calculates the length of "docs" for every term
db.new_inverted_index.update({term:term_name},{$set:{df:df_count}}); //inserts df for every record
i = i+1;
}
