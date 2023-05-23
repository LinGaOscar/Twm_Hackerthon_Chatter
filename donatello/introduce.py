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
    1.Summarize your self-introduction based on the provided information within 100 words.
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

introduce_chain = SimpleSequentialChain(chains=[chain0,chain1,chain2,chain3], verbose=True)