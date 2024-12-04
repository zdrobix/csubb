using Task = lab9.model.Task;

namespace lab9.decorator
{
    internal interface ITaskRunner
    {
        void ExecuteOneTask();
        void ExecuteAll();
        void AddTask(Task t);
        bool HasTask();
    }
}
