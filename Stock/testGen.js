var op = ["buy", "sell", "buy", "sell"];
var id = [2, 3, 4, 5, 6];
var symbol = ['RENA1', 'SAIPA', 'IRAN'];
var type = ['IOC', 'GTC', 'MPO'] ;

console.log('add 1 admin adminiam');
console.log('deposit  1 100000');
console.log('add 2 ali razavi');
console.log('deposit 2 10000000');
console.log('add 3 akbar ghassab');
console.log('deposit 3 300000');
console.log('add 4 joe heart');
console.log('deposit 4 1000000');
console.log('add 5 cris bale');
console.log('deposit 5 700000');
console.log('add 6 feri tori');
console.log('deposit 6 70000');

console.log('sell 1 RENA1 100 500 GTC');
console.log('sell 1 RENA1 200 400 GTC');
console.log('sell 1 SAIPA 500 600 GTC');
console.log('sell 1 SAIPA 400 700 GTC');

console.log('sell 1 IRAN  250 1000 GTC');
console.log('sell 1 RENA1 330 1400 GTC');
console.log('sell 1 SAIPA 470 1600 GTC');
console.log('sell 1 IRAN 400 700 GTC');



for(var i=0 ; i<50 ; i++){
	console.log(op[rnd(4)], id[rnd(5)], symbol[rnd(3)], rnd(300), rnd(200), type[rnd(3)]);
}

console.log('list');

function rnd (num) {
	return Math.floor((Math.random() * num)); 
}