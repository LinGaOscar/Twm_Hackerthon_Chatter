from dotenv import load_dotenv
from langchain.document_loaders import DirectoryLoader,TextLoader
from langchain.text_splitter import CharacterTextSplitter
from langchain.embeddings import OpenAIEmbeddings
from langchain.vectorstores import Chroma
from langchain.chains import RetrievalQA
from langchain import OpenAI

load_dotenv()
# embedding=OpenAIEmbeddings();
# text = "你好!我是Oscar"
# doc_embedding = embedding.embed_documents([text])

# print(doc_embedding)

embeddings = OpenAIEmbeddings();
# filename start with 12
loader = DirectoryLoader('./',glob='**/12*.txt')

documents = loader.load()
# print(len(documents))

text_splitter = CharacterTextSplitter(chunk_size=250, chunk_overlap=0)
texts = text_splitter.split_documents(documents)

# Chroma vetor store
vecstore = Chroma.from_documents(texts,embeddings)
qa = RetrievalQA.from_chain_type(
    llm=OpenAI(),
    chain_type='stuff',
    retriever = vecstore.as_retriever()
)

def query(q):
    print("Query: ",q)
    print("Answer: ",qa.run(q))


query('我怎麼知道巨蟹座是喜歡我?')
query('跟射手座約會要注意麼?')

'''  model_name="text-davinci-003",temperature=0
Query:  我怎麼知道巨蟹座是喜歡我?
Answer:   巨蟹座會了解你的所有喜好，為你做飯，準備一些驚喜給你，還有為你做的專屬便當，冬天有暖暖包、夏天有冰飲料，代表巨蟹已經在你不知不覺中想好了一系列暖心的付出計畫了！
Query:  如果我的對象是射手座,我們要如何維持感情?
Answer:   射手座喜歡欺負對方、有時候假裝不理對方，他們也希望對方能強烈的感覺到他對你的好，並且也只對他一個人好。所以，如果你的對象是射手座，你們要維持感情，你可以偷偷準備一些驚喜給他，並且接受他的欺負，也要強烈的表達你對他的好，並且只對他一個人好。
'''

'''
Query:  我怎麼知道巨蟹座是喜歡我?
Answer:   巨蟹座會了解你的所有喜好，為你做飯、給你貼心的小物，還會親自煮熱飲給你喝，以及做專屬便當，這些都是巨蟹座表達喜歡你的方式。
Query:  如果我的對象是射手座要如何維持感情?
Answer:   射手座想要維持感情的熱度，可以偷偷準備一些驚喜給對方，喜歡欺負對方，有時候也假裝不理對方，但要讓對方感受到你的好，且只對他一個人好。
'''
