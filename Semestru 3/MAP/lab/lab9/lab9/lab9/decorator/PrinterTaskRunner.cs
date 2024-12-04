using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using lab9.model;

namespace lab9.decorator
{
    internal class PrinterTaskRunner : AbstractTaskRunner
    {

        public PrinterTaskRunner(ITaskRunner taskRunner) : base(taskRunner)
        {
        }

        public void ExecuteOneTask()
        {
            ExecuteOneTask();
            Console.WriteLine("Task executed at ");
        }
    }
}
