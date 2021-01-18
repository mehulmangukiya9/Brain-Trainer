var express = require('express');   //helps by wrapping of html & provides methods like post
const app= express();
var mysql = require('mysql');

var bodyparser = require('body-parser');

var connection = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "",
    database: 'braintrainertrial'
});

app.use(bodyparser.json());  //helps to read the data object and convert in json
app.use(bodyparser.urlencoded({extended:true}));


/* rehister */
app.post("/register/",(req,res,next) => {
    /*
    var data = req.body;
    var password = data.password;
    var email = data.email;
    */

    //actual app
      var data = req.body;
      
      var name = data.name;
      var gameTime = data.gameTime;
      var score = data.score;

    
    connection.query("select * from gametable where name = ? ",[name], function(err,result, fields){
        connection.on('error',(err)=>{
            console.log("[mysql error]",err); 
    });
    

    /*connection.query("select * from login_info where email = ?",[email], function(err,result, fields){
        connection.on('error',(err)=>{
            console.log("[mysql error]",err); 
    });*/

    if(result && result.length){
        res.json("user already exists")
    }
    else{
        var insert_cmd = " insert into gametable (name, gameTime, score) values (?,?,?)";
        values  = [name, gameTime, score];

        console.log("executing:" +insert_cmd + "  "+values);
        connection.query(insert_cmd, values, (err, results, fields)=>{
            connection.on('err', (err)=>{
                console.log('[mysql error]',err);
            });

            res.json("registered.");
            console.log("registration successful");
        });
    }
});

});

//login --
    app.post("/login/",(req,res,next) => {
    
      var data = req.body;
      var gameTime = data.gameTime;
      var name = data.name;
      var score = data.score;

    connection.query("select * from gametable where name = ?",[name], function(err,result, fields){
        connection.on('error',(err)=>{
            console.log("[mysql error]",err); 
    });

    if(result && result.length){
        console.log(result);

        if(score==result[0].score){
            res.json("user logged in");
            res.end;
        }else{
            res.json("you have entered wrong score");
            res.end;
        }
    }
    else{
        res.json("user not found");
        res.end;
    }
});
});

var server = app.listen(3000, () => {
    console.log("running at http://localhost:3000")
});