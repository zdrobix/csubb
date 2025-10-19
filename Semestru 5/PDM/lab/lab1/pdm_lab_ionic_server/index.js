
const express = require('express');
const cors = require('cors');
const fs = require('fs');
const path = require('path');
const { Server } = require('socket.io');
const http = require('http');

const app = express();
const PORT = 3000;

const server = http.createServer(app);
const io = new Server(server, {
  cors: {
    origin: 'https://localhost:8100', 
  }
});

app.use(cors());
app.use(express.json());

const carsFilePath = path.join(__dirname, 'data', 'Cars.json');

app.get('/api/cars', (req, res) => {
    let carsData = [];

    try {
      const fileContent = fs.readFileSync(carsFilePath, 'utf8');
      carsData = JSON.parse(fileContent);
    } catch (err) {
      console.error('Error reading Cars.json:', err);
    }

    res.json({ data: carsData });
});

fs.watch(carsFilePath, (eventType, filename) => {
  if (filename && eventType === 'change') {
    console.log("Cars.json updated.");
    try {
      const fileContentUpdated= fs.readFileSync(carsFilePath, 'utf8');
      const newCarsData = JSON.parse(fileContentUpdated);

      io.emit('carsUpdate', { data: newCarsData });
      console.log("Sent new data.");
    } catch (err) {
      console.error('Error reading Cars.json:', err);
    }
  }
})

io.on('connection', (socket) => {
  console.log('New client connected: ', socket.id);

  try {
    const carsData = JSON.parse(fs.readFileSync(carsFilePath, 'utf8'));
    socket.emit('carsUpdate', { data: carsData });
  } catch (err) {
    console.error('Error sending old data:', err);

  socket.on('disconnect', () => {
    console.log('Client disconnected: ', socket.id);
  });
  }
});

server.listen(PORT, () => {
  console.log(`Server started on http://localhost:${PORT}`);
});
