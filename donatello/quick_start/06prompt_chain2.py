import os
os.environ["OPENAI_API_KEY"] = "sk--XLAZ7MP4oIrQQJtDTzLyT3BlbkFJB9Wgr30is9riq5GyPEgx"

from langchain.llms import OpenAI
from langchain import PromptTemplate
from langchain.chains import LLMChain
from langchain.chains import SimpleSequentialChain

llm = OpenAI(temperature=0.9, model_name="text-davinci-003")

prompt0 = PromptTemplate(
    input_variables=["value"],
    template=
    """
    Please convert the text to English
    '''{value}'''
    """,
)
chain0 = LLMChain(llm=llm, prompt=prompt0,verbose=True)

prompt1 = PromptTemplate(
    input_variables=["value"],
    template=
    """
    1.Please introduce yourself in your own words based on the provided information.
    2.Talk about your blood type, zodiac sign, and interests. You can also mention your personality traits, profession, and family background.
    '''{value}'''
    """,
)
chain1 = LLMChain(llm=llm, prompt=prompt1,verbose=True)

prompt2 = PromptTemplate(
    input_variables=["value"],
    template=
    """
    1.Summarize your self-introduction based on the provided information.
    2.Discuss your attitude towards life and the importance of pursuing your passions from a first-person perspective.
    '''{value}'''
    """,
)
chain2 = LLMChain(llm=llm, prompt=prompt2,verbose=True)

prompt3 = PromptTemplate(
    input_variables=["value"],
    template=
    """
    Please translate the text into Traditional Chinese.
    '''{value}'''
    """,
)
chain3 = LLMChain(llm=llm, prompt=prompt3,verbose=True)

overall_chain = SimpleSequentialChain(chains=[chain0,chain1,chain2,chain3], verbose=True)
text="Max,27歲,180公分,摩羯座,O型,男性,異性戀,個性活力充沛、開朗友善、積極向上,興趣戶外活動，爬山、露營、徒步旅行。喜歡小動物、狗、貓,自由職業者，工作攝影和網路營銷,相信只有在追求自己喜愛的事物的時候，才能真正享受生命的美好。"

output = overall_chain.run(text)
print(output)
#print(overall_chain.run(text))