package com.example.luthiers.bakingapp.pojos;

/*
* This pojo needs:
* -> quantity: type: float
* -> measure: type: String
* -> ingredient: type: String
*/
public class Ingredient {
    
    private Float mQuantity;
    private String mMeasure;
    private String mIngredient;
    
    //Add a public constructor
    public Ingredient(Float quantity, String measure, String ingredient) {
        mQuantity = quantity;
        mMeasure = measure;
        mIngredient = ingredient;
    }
    
    //Add all getters and setters
    
    public Float getQuantity() {
        return mQuantity;
    }
    
    public void setQuantity(Float quantity) {
        mQuantity = quantity;
    }
    
    public String getMeasure() {
        return mMeasure;
    }
    
    public void setMeasure(String measure) {
        mMeasure = measure;
    }
    
    public String getIngredient() {
        return mIngredient;
    }
    
    public void setIngredient(String ingredient) {
        mIngredient = ingredient;
    }
    
}