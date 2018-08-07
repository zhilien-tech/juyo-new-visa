const Socket = (() => {
    class Socket {
        constructor() {
            
        }
        connect(BASEURL) {
            if (!('WebSocket' in window)) return console.error('Websocket not supported');
            if (!BASEURL || BASEURL === '' || typeof BASEURL != 'string') return console.error('arguments 1 not type string');
            return new WebSocket(BASEURL);
        }
    }

    return Socket;
})();

const layerFn = (() => {
    return {
        close(fn) {
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
            parent.successCallBack();
            fn && fn();
		}
    };
})();