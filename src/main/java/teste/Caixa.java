package teste;

public class Caixa<T> {
    private T item;
    private Class<T> classes;

    public Caixa(Class<T> classes) {
        this.classes = classes;
    }

    public String NomeClasse() {
        return this.classes.getName();
    }

    public void colocar(T item){
        this.item=item;
    }
    
    public T pegar(){
        return item;
    }
}
