import numpy
import pandas as pd
from sklearn import linear_model
#survey results are n = 20, each in {0,1,2,3,4}

reg = linear_model.LinearRegression()
regRidge = linear_model.Ridge(alpha=0.5)
regLasso = linear_model.Lasso(alpha=0.1)

# Roman: this code takes the data from my sample CSV and prints
# the array we need for ML. Please update to this when you are ready!
df = pd.read_csv('myCopy.csv')
print(df.values[:,11:])

#mock survey data for 5 people
mock_survey_data = [[4,1,0,4,0,1,3,4,4,3,3,2,3,0,1,0,0,1,0,3], #"democrat"
                    [0,3,3,0,4,3,0,1,0,0,0,0,1,3,3,3,4,3,3,1], #"republican"
                    [1,3,3,1,4,2,0,1,0,1,1,0,1,4,2,3,4,3,2,1], #"republican"
                    [2,2,1,1,2,3,2,3,2,2,2,2,2,2,3,2,2,3,3,3], #"moderate republican"
                    [3,1,1,3,2,2,3,3,3,3,3,3,3,1,2,1,1,2,1,2]] #"moderate democrat"

#mock opinions on immigration
mock_opinions_immigration = [2,7,7,5,3]
#mock_opinions_immigration = [7,2,2,4,8]

reg.fit(mock_survey_data, mock_opinions_immigration)
regRidge.fit(mock_survey_data, mock_opinions_immigration)
regLasso.fit(mock_survey_data, mock_opinions_immigration)


print("oLS coefficients: ")
print(reg.coef_)
print("LS+ridge coefficients: ")
print(regRidge.coef_)
print("LASSO coefficients: ")
print(regLasso.coef_)

#mock new person "democrat"
mock_new_data1 = [4,1,0,3,1,2,3,3,4,3,3,3,3,1,1,1,1,2,1,3]
#predict their opinion on immigration
pred1 = reg.predict(numpy.reshape(mock_new_data1, (1,-1)))
predRidge1 = regRidge.predict(numpy.reshape(mock_new_data1, (1,-1)))
predLasso1 = regLasso.predict(numpy.reshape(mock_new_data1, (1,-1)))
print("new prediction oLS, mock democrat: " + str(pred1))
print("new prediction LS+ridge, mock democrat: " + str(predRidge1))
print("new prediction LASSO, mock democrat: " + str(predLasso1))

#mock new person "republican"
mock_new_data2 = [0,4,3,0,3,3,1,1,0,0,0,1,1,3,3,4,3,3,2,1]
#predict their opinion on immigration
pred2 = reg.predict(numpy.reshape(mock_new_data2, (1,-1)))
predRidge2 = regRidge.predict(numpy.reshape(mock_new_data2, (1,-1)))
predLasso2 = regLasso.predict(numpy.reshape(mock_new_data2, (1,-1)))
print("new prediction oLS, mock republican: " + str(pred2))
print("new prediction LS+ridge, mock republican: " + str(predRidge2))
print("new prediction LASSO, mock republican: " + str(predLasso2))
