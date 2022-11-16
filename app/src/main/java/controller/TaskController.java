package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author kelvi
 */
public class TaskController {
    
    public void save(Task task ){
        
        String sql = "INSERT INTO tasks (idProject, "
                + "name,"
                + " description,"
                + " completed, "
                + " notes,"
                + " deadline,"
                + " createdAt,"
                + " updatedAt) VALUES (?,?,?,?,?,?,?,?) ";
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.getIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.execute();
        
            } catch (Exception ex) {
                throw new RuntimeException("Erro ao Inserir a tarefa " + ex.getMessage(),ex );
                        
        } finally {
            ConnectionFactory.closeConnection(connection, statement);

        }
    }
    
    public void update(Task task){
        
        String sql = " UPDATE tasks SET "
                + "idProject = ?,"
                + "name = ?,"
                + "description = ?,"
                + "completed = ?,"
                + "notes = ?,"
                + "deadline = ?,"
                + "createdAt = ?,"
                + "updatedAt = ? "
                + "Where id = ?";
        
         Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            /*Estabelecendo a conexão com o Banco de dados*/
            connection = ConnectionFactory.getConnection();
            
            /*Preparando a query*/
            statement = connection.prepareStatement(sql);
            
            /*Setando os Valores do Statement */
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.getIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());
            
            //Executando a query
            statement.execute();
        
            } catch (Exception ex) {
                throw new RuntimeException("Erro ao Atualizar a tarefa " + ex.getMessage(),ex );
                        
        } finally {
            ConnectionFactory.closeConnection(connection, statement);

        }
    }
    
    public void removeById(int taskId){
        String sql = "DELETE FROM tasks WHERE id = ?";
        Connection connection = null;
        PreparedStatement  statement = null;
        
        try {
            /*Criação da conexão com o Banco de dados*/
            connection = ConnectionFactory.getConnection();
            
            /* Preparando a query*/
            statement = connection.prepareStatement(sql);
            
            /*Setando os valore*/
            statement.setInt(1, taskId);
            
            /*Execuntando a query*/
            statement.execute();
            
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar a tarefa" + ex.getMessage(),ex);
        }
        finally{
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public List<Task> getAll(int idProject){
        String sql ="SELECT * FROM tasks WHERE idProject = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        //Lista de tarefas que será retornada ao final do método
        List<Task> tasks = new ArrayList<Task>();

        try {
            /*Criando a conexão*/
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            /*Setando o valor do filtro*/
            statement.setInt(1,idProject);
            
            /*Valor retornado pela execução da query*/
            resultSet = statement.executeQuery();

            /*preenchendo a lista com os valores devolvidos pela query */
            while (resultSet.next()) {

                Task task = new Task(
                resultSet.getInt("id"),
                resultSet.getInt("idProject"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getString("notes"),
                resultSet.getBoolean("completed"),
                resultSet.getDate("deadline"),
                resultSet.getDate("createdAt"),
                resultSet.getDate("updatedAt")

                );
                tasks.add(task);
            }

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao buscar as tarefas " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }

        return tasks;
    }
}
