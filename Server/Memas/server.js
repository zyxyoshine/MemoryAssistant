var server = require('http').createServer();
var io = require('socket.io')(server);

io.on('connection', function(socket) {

	console.log('Socket {0} connected'.format(socket.id));

	if (socket.handshake.query.username && socket.handshake.query.userid) {
		console.log("<" + (new Date()).Format("MM-dd hh:mm:ss") + ">" + "User connecting: {0}, identifier of device: {1}".format(socket.handshake.query.username, socket.handshake.query.userid));
		socket.username = socket.handshake.query.username;
		socket.userid = socket.handshake.query.userid;

		socket.broadcast.emit('isOnline', {
			username: socket.username,
			userid: socket.userid,
			time: (new Date()).Format("MM-dd hh:mm:ss")
		});
	}
	else {
		socket.emit('disconnect', { status: false, message: "Invalid connection parameters" });
	}

	socket.on('sendMessage', function(data) {
		console.log("<" + (new Date()).Format("MM-dd hh:mm:ss") + ">" + "User {0} sended a message".format(socket.id));
		if (data && data.message) {
			io.sockets.emit('sendMessage', {
				username: socket.username,
				userid: socket.userid,
				message: data.message,
				time: (new Date()).Format("MM-dd hh:mm:ss")
			});
		}
	});

	socket.on('disconnect', function() {
		console.log("<" + (new Date()).Format("MM-dd hh:mm:ss") + ">" + "Socket {0} disconnected -> {1} - {2}".format(socket.id, socket.username, socket.userid));
		socket.broadcast.emit('isOffline', {
			username: socket.username,
			userid: socket.userid,
			time: (new Date()).Format("MM-dd hh:mm:ss")
		});
	});
});

server.listen(2652, '0.0.0.0', function() {
	console.log("<" + (new Date()).Format("MM-dd hh:mm:ss") + ">" + "Server listen on port 2652");
});

String.prototype.format = function() {
    var formatted = this;
    for (var i = 0; i < arguments.length; ++i) {
        var regex = new RegExp('\\{'+i+'\\}', 'gi');
        formatted = formatted.replace(regex, arguments[i]);
    }
    return formatted;
};

Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
