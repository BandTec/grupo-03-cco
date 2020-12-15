const express = require('express');
const mysql = require('mysql');

// configuração de conexão

// conexão
db.connect((err) => {
    if(err){
       console.log("cu");
    }
    console.log('Mysql Conectado');
});

const app = express();

// Criar Database
app.get('/createdb', (req, res) => {
    let sql = 'CREATE DATABASE x';
    db.query(sql, (err, result) => {
        if(err) throw err;
        console.log(result);
        res.send('Banco de dados criado...');
    });
});

// Criar tabela
app.get('/createpoststable', (req, res) => {
    let sql = 'CREATE TABLE urubu100(id int AUTO_INCREMENT, nome VARCHAR(50), Tipo VARCHAR(50), PRIMARY KEY(id))';
    db.query(sql, (err, result) => {
        if(err) throw err;
        console.log(result);
        res.send('Tabela criada.');
    });
});

// Inserir insert 1
app.get('/addpost1', (req, res) => {
    let post = {title:'Insert 1', body:'Insert de número 1'};
    let sql = 'INSERT INTO posts SET ?';
    let query = db.query(sql, post, (err, result) => {
        if(err) throw err;
        console.log(result);
        res.send('Primeiro insert realizado');
    });
});

// Inserir insert 2
app.get('/addpost2', (req, res) => {
    let post = {title:'Insert 2', body:'Insert de número 2'};
    let sql = 'INSERT INTO posts SET ?';
    let query = db.query(sql, post, (err, result) => {
        if(err) throw err;
        console.log(result);
        res.send('Segundo insert realizado');
    });
});

// Select
app.get('/getposts', (req, res) => {
    let sql = `SELECT * FROM DATASET_COMP where id = ${4}`;
    let query = db.query(sql, (err, results) => {
        if(err) throw err;
        console.log(results);
        res.send('select geral relizado');
        
    });
});

app.get('/getposts2', (req, res) => {
    let sql = 'SELECT * FROM DATASET_COMP';
    let query = db.query(sql, (err, results) => {
        // if(err) throw err;
        console.log(results);
        res.send('select geral relizado');
        return results.json();
    });
});

// Select  personalizado exemplo de id
app.get('/getpost/:id', (req, res) => {
    let sql = `SELECT * FROM posts WHERE id = ${req.params.id}`;
    let query = db.query(sql, (err, result) => {
        if(err) throw err;
        console.log(result);
        res.send('Select por id realizado');
    });
});

// Update exemplo de id
app.get('/updatepost/:id', (req, res) => {
    let newTitle = 'Updated Title';
    let sql = `UPDATE posts SET title = '${newTitle}' WHERE id = ${req.params.id}`;
    let query = db.query(sql, (err, result) => {
        if(err) throw err;
        console.log(result);
        res.send('Update por id realizado');
    });
});

// Delete post
app.get('/deletepost/:id', (req, res) => {
    let newTitle = 'Updated Title';
    let sql = `DELETE FROM posts WHERE id = ${req.params.id}`;
    let query = db.query(sql, (err, result) => {
        if(err) throw err;
        console.log(result);
        res.send('registro deletado');
    });
});

app.get('/droptable', (req, res) => {
    let newTitle = 'Deletar tabela';
    let sql = `drop table urubu100`;
    let query = db.query(sql, (err, result) => {
        if(err) throw err;
        console.log(result);
        res.send('Tabela deletado');
    });
});

app.listen('3000', () => {
    console.log('Servidor iniciado na porta 3000');
});

