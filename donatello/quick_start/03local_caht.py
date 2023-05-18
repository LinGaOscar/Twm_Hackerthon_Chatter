import os
os.environ["OPENAI_API_KEY"] ="sk---wSJHhidTmqOgquRSXVRdT3BlbkFJjIJZtoxi624ebXJEW6Mw"

from langchain.embeddings.openai import OpenAIEmbeddings
from langchain.vectorstores import Chroma
from langchain.text_splitter import CharacterTextSplitter
from langchain import OpenAI,VectorDBQA
from langchain.document_loaders import DirectoryLoader
from langchain.chains import RetrievalQA

# 加载文件夹中的所有txt类型的文件
loader = DirectoryLoader('./', glob='**/*.txt')
# 将数据转成 document 对象，每个文件会作为一个 document
documents = loader.load()

# 初始化加载器
text_splitter = CharacterTextSplitter(chunk_size=100, chunk_overlap=0)
# 切割加载的 document
split_docs = text_splitter.split_documents(documents)

# 初始化 openai 的 embeddings 对象
embeddings = OpenAIEmbeddings()
# 将 document 通过 openai 的 embeddings 对象计算 embedding 向量信息并临时存入 Chroma 向量数据库，用于后续匹配查询
docsearch = Chroma.from_documents(split_docs, embeddings)

# 创建问答对象
qa = VectorDBQA.from_chain_type(llm=OpenAI(batch_size=5), chain_type="map_rerank", vectorstore=docsearch,return_source_documents=True)
# 进行问答
#result = qa({"query": "誰是石內卜"})
result = qa({"query": "誰是普丁"})

print(result)

'''{'query': '誰是石內卜', 'result': ' 石內卜是日本作家', 'source_documents': [Document(page_content='石內卜生前日記曝光！排隊買《哈利波特》等太久⋯1句話直接見經理\n\n記者林育綾／綜合報導', metadata={'source': '03news.txt'}), Document(p
   age_content='2003年7月30日\n\n上午7點：車子來接。石內卜／路平的教室。', metadata={'source': '03news.txt'}), Document(page_content='再次和喬安娜．羅琳談話，她有些緊張地讓我一窺石內卜的人生背景。和她說話時，感覺她不是發明故事的人，而
   是生活在故事之中的人。她不過是渠道——滿溢著「這個啊，其實他小時候發生了這個那個」——一次也沒說「我想讓他如何如何……」', metadata={'source': '03news.txt'}), Document(page_content='在《哈利波特》中飾演石內卜教授的英國演員艾倫瑞克曼
   （Alan Rickman），2016年因胰臟癌去世，不過他生前橫跨40年的私人日記集結成書，近來在台發行中文版。日記中除了有對電影、生活、藝術的見解，還有不少幽默的事件，像是曾為了在書店買最後一集《哈利波特》等太久，而用了「小手段」直接見經理。', m
   etadata={'source': '03news.txt'})]}
'''