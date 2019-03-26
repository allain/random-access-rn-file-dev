using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Random.Access.Rn.File.RNRandomAccessRnFile
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNRandomAccessRnFileModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNRandomAccessRnFileModule"/>.
        /// </summary>
        internal RNRandomAccessRnFileModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNRandomAccessRnFile";
            }
        }
    }
}
