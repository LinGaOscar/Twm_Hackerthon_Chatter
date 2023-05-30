from langchain.llms import OpenAI
from langchain import PromptTemplate
from langchain.chains import LLMChain
from langchain.chains import SequentialChain

llm = OpenAI(temperature=0.9, model_name="text-davinci-003")

prompt4 = PromptTemplate(
    input_variables=["target","value"],
    template=
    """
    Simulate {target}'s response in the following conversation.
    Add some details to enhance the conversation.
    Keep the answers concise
    '''{value}'''
    """,
)
chain4 = LLMChain(llm=llm, prompt=prompt4,verbose=True,output_key="result")

prompt3 = PromptTemplate(
    input_variables=["result"],
    template=
    """
    Please translate the text into Traditional Chinese.
    '''{result}'''
    """,
)

chain3 = LLMChain(llm=llm, prompt=prompt3,verbose=True)

overall_chain = SequentialChain(chains=[chain4,chain3],input_variables=["target", "value"], verbose=True)
text = """Max : 你呢? 平時有喜歡的做的事情嗎?
        Cindy: 平時就下班 偶爾運個小動 回家看一下書然後放空睡覺
        Cindy: 周未跟朋友約去咖啡廳或是到處走走
        Cindy:阿我喜歡吃東西
        Cindy:我也喜歡看電影 就一些很平常的事情耶其實
        Max: 我也是啦 不用那麼拘束"""

output = overall_chain.run(target="max",value=text)
print(output)