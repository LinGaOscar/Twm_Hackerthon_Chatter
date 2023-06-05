import whisper
from gtts import gTTS
import os

#install ffmpeg and set env.path
#https://www.geeksforgeeks.org/how-to-install-ffmpeg-on-windows/
model = whisper.load_model("base")
result = model.transcribe("10buildings.mp3")
print(result["text"])

mytext = result["text"]
audio = gTTS(text=mytext, lang="en", slow=False)
audio.save("example.mp3")

#tts=gTTS(text='生日快樂', lang='zh')
#tts=gTTS(text='ありがとう', lang='ja')
#tts.save("example.mp3")
os.system("start example.mp3")