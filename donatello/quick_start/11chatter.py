import pyaudio
import wave
import keyboard
from pydub import AudioSegment
from pydub.playback import play

CHUNK = 1024
FORMAT = pyaudio.paInt16
CHANNELS = 1
RATE = 44100

frames = []

def start_recording():
    global frames
    frames = []
    p = pyaudio.PyAudio()

    stream = p.open(format=FORMAT,
                    channels=CHANNELS,
                    rate=RATE,
                    input=True,
                    frames_per_buffer=CHUNK)

    print("Recording started...")

    while True:
        if keyboard.is_pressed(' '):  # 按下空白鍵開始錄音
            break

    while True:
        if keyboard.is_pressed(' '):  # 再次按下空白鍵停止錄音
            break
        data = stream.read(CHUNK)
        frames.append(data)

    print("Recording finished.")

    stream.stop_stream()
    stream.close()
    p.terminate()

    # 儲存錄音檔案為WAV格式
    output_file = 'output.wav'
    wf = wave.open(output_file, 'wb')
    wf.setnchannels(CHANNELS)
    wf.setsampwidth(p.get_sample_size(FORMAT))
    wf.setframerate(RATE)
    wf.writeframes(b''.join(frames))
    wf.close()

    # 將錄音轉換為MP3格式
    audio = AudioSegment.from_wav(output_file)
    output_file_mp3 = 'output.mp3'
    audio.export(output_file_mp3, format='mp3')
    print("File saved as", output_file_mp3)

    # 播放錄音檔案
    play(audio)

# 監聽空白鍵，按下後開始錄音，再次按下後停止錄音
keyboard.wait(' ')
start_recording()