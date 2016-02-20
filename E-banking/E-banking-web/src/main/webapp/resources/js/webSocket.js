var webSocket = new WebSocket("ws://192.168.0.108:8083/Telefonia/serversocket");
            var messagesTextArea = document.getElementById("menssagesTextArea");
            webSocket.onopen = function (message) {
                processOpen(message);
            };
            webSocket.onclose = function (message) {
                processClose(message);
                            };
            webSocket.onerror = function (message) {
                processError(message);
            };
            webSocket.onmessage = function (message) {
                processMessage(message);
            };
            function processOpen(message) {
                messagesTextArea.value += "Server Connect..." + "\n";

            }
            function processError(message) {
                messagesTextArea.value += "error..." + "\n";

            }
            function processClose(message) {
                messagesTextArea.value += "Server Desconectado..." + "\n";

            }
            function processMessage(message) {
                messagesTextArea.value += "Recibido del servidor..." + message.data +"\n";                
                if(message.data == "NO"){
                    alert("El número telefónico no existe");
                }

            }
            function sendMessage() {
                if (textTelefono.value !== "close") {
                    var mensaje = textTelefono.value + "|" + textValor.value;
                    webSocket.send(mensaje);
                    messagesTextArea.value += "Enviado al servidor..." +mensaje +"\n";
                    textTelefono.value = "";
                    textValor.value = "";
                } else
                    webSocket.close();
            }