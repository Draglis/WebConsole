function loadContentFile() {
  var contentDiv = document.getElementById("content");
  if (typeof contentDiv.contentDocument !== "undefined") {
    contentDiv.innerHTML =
      '<object type="type/html" data="content.txt"></object>';
  } else {
    fetch("content.txt")
      .then((response) => {
        if (response.ok) {
          return response.text();
        } else {
          throw new Error("Content file not found");
        }
      })
      .then((text) => {
        contentDiv.innerHTML = formatLogLines(text);
        clearInterval(timer); // Disable the timer
      })
      .catch(error => {
        contentDiv.textContent = "This extension module is not supported";
        clearInterval(timer); // Disable the timer
      });
  }
}

function formatLogLines(text) {
  var lines = text.split("\n");
  var formattedLines = lines.map(function (line) {
    return formatLogLine(line);
  });
  return formattedLines.join("");
}

function formatLogLine(line) {
  var trimmedLine = line.trim(); // Trim leading and trailing spaces
  if (trimmedLine !== "") {
    var timestampStartIndex = trimmedLine.indexOf("[");
    var timestampEndIndex = trimmedLine.indexOf("]");
    if (
      timestampStartIndex !== -1 &&
      timestampEndIndex !== -1 &&
      timestampEndIndex > timestampStartIndex
    ) {
      var timestamp = trimmedLine.substring(
        timestampStartIndex + 1,
        timestampEndIndex
      );
      var restOfLine = trimmedLine.substring(timestampEndIndex + 1).trim();

      var parts = restOfLine.split(" ");
      if (parts.length >= 3) {
        var playerName = parts[0];
        var direction = parts[1];
        var logMessage = parts.slice(2).join(" ");

        var formattedLine = '<div class="log-line">';
        formattedLine += '<span class="timestamp">[' + timestamp + "]</span>";
        formattedLine += '<span class="player-name">' + playerName + "</span>";
        formattedLine += '<span class="direction">' + direction + "</span>";
        formattedLine +=
          '<span class="log-message">' +
          formatLogMessage(logMessage) +
          "</span>";
        formattedLine += "</div>";

        return formattedLine;
      }
    }
  }
  return "";
}

function formatLogMessage(logMessage) {
  var parts = logMessage.split(" | ");
  if (parts.length === 4) {
    var server = parts[0].trim();
    var name = parts[1].trim();
    var id = parts[2].trim();
    var data = parts[3].trim();
    var formattedMessage =
      server +
      '<div class="log-message-box">' +
      "Name: " +
      name +
      "<br>" +
      "Id: " +
      id +
      "<br>" +
      "Data: " +
      data +
      "</div>";
    return formattedMessage;
  }
  return logMessage;
}

var timer = setInterval(loadContentFile, 1000); // Start the timer
window.onload = loadContentFile(); // Load the content immediately
